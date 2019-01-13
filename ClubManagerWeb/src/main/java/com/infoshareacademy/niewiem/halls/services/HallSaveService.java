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

    public Integer save(Hall hall) {
        // todo: validate me like you validate your French girls!
        // id should be null, otherwise it's not save but update!
        // name should not be null or empty
        // if image null or empty- put in the default image
        // check if image actually exist on disk
        return hallDao.save(hall);
    }

    public Integer addNewHall(HttpServletRequest req, List<String> errors) throws IOException, ServletException {
        Part part = req.getPart("image");
        // todo: should we validate String in here whether it is null or not?
        //  This would probably mean exceptions though...

        String name = req.getParameter("name");
        if (hallValidator.validateStringIsEmpty(name, "Hall's name", errors)) {
            return -1;
        }

        Hall hall = new Hall();
        hall.setName(name);

        try {
            File image = fileUploadProcessor.uploadImageFile(part);
            String imageName = image.getName();
            hall.setImageURL("/images/" + imageName);
        } catch (HallImageNotFound userImageNotFound) {
            LOG.warn("Image not found");
        }

        return save(hall);
    }
}