package com.infoshareacademy.niewiem.services.validators;

import com.infoshareacademy.niewiem.enums.TableType;

import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;

@Stateless
public class InputValidator {
    public Integer reqIntegerValidator(String integer){
        // todo: validate me!
        return Integer.valueOf(integer);
    }

    public TableType reqTableTypeValidator(String type) {
        // todo: validate me!
        return TableType.valueOf(type);
    }

    public Part reqImageValidator(HttpServletRequest req) throws IOException, ServletException {
        // todo: validate me!
        return req.getPart("image");
    }
}
