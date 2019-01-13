package com.infoshareacademy.niewiem.halls.services;

import com.infoshareacademy.niewiem.domain.Hall;
import com.infoshareacademy.niewiem.halls.dao.HallDao;
import com.infoshareacademy.niewiem.halls.exceptions.HallImageNotFound;
import com.infoshareacademy.niewiem.halls.validators.HallValidator;
import com.infoshareacademy.niewiem.images.cdi.FileUploadProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Stateless
public class HallSaveService {
    private static final Logger LOG = LoggerFactory.getLogger(HallSaveService.class);

    @Inject
    private HallDao hallDao;

    @Inject
    private FileUploadProcessor fileUploadProcessor;

    @Inject
    private HallValidator hallValidator;

    public void saveWithoutValidation(Hall hall) {
        hallDao.save(hall);
    }

    public boolean createNewHall(HttpServletRequest req, List<String> errors) throws IOException, ServletException {
        String name = req.getParameter("name");
        LOG.info("Got name: {}", name);
        if (hallValidator.validateStringIsEmpty(name, "Hall's name", errors)) {
            return false;
        }

        Hall hall = new Hall();
        hall.setName(name);

        Part part = req.getPart("image");

        try {
            File image = fileUploadProcessor.uploadImageFile(part);
            String imageName = image.getName();
            hall.setImageURL("/images/" + imageName);
        } catch (HallImageNotFound userImageNotFound) {
            LOG.warn("Image not found");
            errors.add("Image not found.");
        }

        return hallDao.save(hall) > -1;
    }
}