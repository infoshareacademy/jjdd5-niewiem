package com.infoshareacademy.niewiem.services.validators;

import com.infoshareacademy.niewiem.enums.TableType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;

@Stateless
public class InputValidator {
    private static final Logger LOG = LoggerFactory.getLogger(InputValidator.class);


    public TableType reqTableTypeValidator(String type) {
        // todo: get rid of me!
        return TableType.valueOf(type);
    }

    public Part reqImageValidator(HttpServletRequest req) throws IOException, ServletException {
        // todo: get rid of me!
        return req.getPart("image");
    }
}