package com.infoshareacademy.niewiem.shared.validators;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public abstract class GenericValidator {
    private static final Logger LOG = LoggerFactory.getLogger(GenericValidator.class);

    public boolean validateNumeric(String param, String requestName, List<String> errors) {
        if (StringUtils.isNumeric(param)) {
            LOG.debug("Requested {} ({}) is numeric", requestName, param);
            return true;
        }
        errors.add("Bad Request. Requested " + requestName + " should be a number.");
        LOG.warn("Bad Request. Requested {} was not numeric.", requestName);
        return false;
    }

    public boolean validateIsNotNumeric(String param, String requestName, List<String> errors) {
        return !validateNumeric(param, requestName, errors);
    }

    public boolean validateStringIsNotEmpty(String nameParam, String requestName, List<String> errors) {
        if (StringUtils.isEmpty(nameParam)) {
            LOG.warn("Invalid requested. {} was empty.", requestName);
            errors.add("Invalid request." + requestName + " cannot be empty.");
            return false;
        }
        LOG.debug("{} was not empty.", requestName);
        return true;
    }

    public boolean validateStringIsEmpty(String nameParam, String requestName, List<String> errors){
        return !validateStringIsNotEmpty(nameParam, requestName, errors);
    }
}