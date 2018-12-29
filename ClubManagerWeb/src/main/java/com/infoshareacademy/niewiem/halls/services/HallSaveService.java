package com.infoshareacademy.niewiem.halls.services;

import com.infoshareacademy.niewiem.images.cdi.FileUploadProcessor;
import com.infoshareacademy.niewiem.halls.dao.HallDao;
import com.infoshareacademy.niewiem.halls.exceptions.HallImageNotFound;
import com.infoshareacademy.niewiem.domain.Hall;
import com.infoshareacademy.niewiem.services.validators.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@Stateless
public class HallSaveService {
    private static final Logger LOG = LoggerFactory.getLogger(HallSaveService.class);

    @Inject
    private HallDao hallDao;

    @Inject
    private FileUploadProcessor fileUploadProcessor;

    @Inject
    private InputValidator inputValidator;


    public Integer save(Hall hall){
        // todo: validate me like you validate your French girls!
        // id should be null, otherwise it's not save but update!
        // name should not be null or empty
        // if image null or empty- put in the default image
        // check if image actually exist on disk
        return hallDao.save(hall);
    }

    public Integer addNewHall(HttpServletRequest req) throws IOException, ServletException {
        // todo: catch Exceptions in input validator!
        Part part = inputValidator.reqImageValidator(req);
        // todo: should we validate String in here whether it is null or not?
        //  This would probably mean exceptions though...
        String name = req.getParameter("name");

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