package com.infoshareacademy.niewiem.services.validators;

import com.infoshareacademy.niewiem.enums.TableType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Stateless
public class InputValidator {
    private static final Logger LOG = LoggerFactory.getLogger(InputValidator.class);

    public Integer reqIntegerValidator(String integer) {
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

    public LocalDateTime reqDateTime(String dateString, String timeString) {
        // todo: validate me!
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate ld = LocalDate.parse(dateString, dateFormat);
        LocalTime lt = LocalTime.parse(timeString, timeFormat);

        return LocalDateTime.of(ld, lt);
    }

    public Long reqLongValidator(String longString) {
        // todo: validate me!
        return Long.valueOf(longString);
    }
}
