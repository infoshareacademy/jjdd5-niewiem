package com.infoshareacademy.niewiem.halls.mappers;

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
public class HallMapper {
    private static final Logger LOG = LoggerFactory.getLogger(HallMapper.class);

    @Inject
    private HallValidator hallValidator;

    @Inject
    private HallDao hallDao;

    @Inject
    private FileUploadProcessor fileUploadProcessor;

    public Hall getHallWithId(HttpServletRequest req, List<String> errors) {
        Hall hall;

        String hidParam = req.getParameter("hid");
        if (hallValidator.validateHidParam(hidParam, errors)) {
            int hid = Integer.parseInt(hidParam);
            hall = hallDao.findById(hid);
        }else{
            return null;
        }

        setName(req, errors, hall);
        setImage(req, errors, hall);

        return hall;
    }

    public Hall getHallWithoutId(HttpServletRequest req, List<String> errors) {
        Hall hall = new Hall();
        if (!setName(req, errors, hall)) {
            return null;
        }
        setImage(req, errors, hall);

        return hall;
    }

    private boolean setName(HttpServletRequest req, List<String> errors, Hall hall) {
        String name = req.getParameter("name");
        LOG.debug("Got name: {}", name);
        if (hallValidator.validateStringIsEmpty(name, "Hall's name", errors)) {
            return false;
        }

        hall.setName(name);
        return true;
    }

    private boolean setImage(HttpServletRequest req, List<String> errors, Hall hall) {
        try {
            Part part = req.getPart("image");
            File image = fileUploadProcessor.uploadImageFile(part);
            String imageName = image.getName();
            hall.setImageURL("/images/" + imageName);
            return true;
        } catch (IOException | ServletException | HallImageNotFound e) {
            LOG.warn("Image not found");
            LOG.warn(e.getMessage());
            errors.add("Image not found.");
            return false;
        }
    }
}
