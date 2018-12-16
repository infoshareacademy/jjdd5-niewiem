package com.infoshareacademy.niewiem.services;

import com.infoshareacademy.niewiem.dao.HallDao;
import com.infoshareacademy.niewiem.exceptions.SavingFailed;
import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.services.validators.HallValidator;
import com.infoshareacademy.niewiem.cdi.FileUploadProcessor;
import com.infoshareacademy.niewiem.dao.HallDao;
import com.infoshareacademy.niewiem.exceptions.HallImageNotFound;
import com.infoshareacademy.niewiem.pojo.Hall;
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
    private HallValidator hallValidator;

    @Inject
    private FileUploadProcessor fileUploadProcessor;

    @Inject
    private InputValidator inputValidator;

    public Integer save(Hall hall) throws SavingFailed {

        if(hallValidator.isHallNotNull(hall)){
            throwException("Hall didn't save because id is not null");
        }

        if(hallValidator.isNameNotNullOrEmpty(hall)){
            throwException("Hall didn't save because name is not null or empty");
        }

        return hallDao.save(hall);
    }

    public Integer addNewHall(HttpServletRequest req) throws IOException, ServletException, SavingFailed {
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

    private void throwException(String message) throws SavingFailed {
        LOG.warn(message);
        throw new SavingFailed(message);
    }
}