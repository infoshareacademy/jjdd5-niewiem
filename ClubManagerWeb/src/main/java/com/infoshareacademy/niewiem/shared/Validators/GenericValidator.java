package com.infoshareacademy.niewiem.shared.Validators;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public abstract class GenericValidator {
    private static final Logger LOG = LoggerFactory.getLogger(GenericValidator.class);

    public boolean validateParamIdIsNumeric(String param, List<String> errors) {
        if (StringUtils.isNumeric(param)) {
            LOG.debug("Requested id ({}) is numeric", param);
            return true;
        }
        errors.add("Bad Request. Requested ID should be a number.");
        LOG.warn("Bad Request. Requested ID was not numeric.");
        return false;
    }

    public boolean validateParamIdIsNotNumeric(String param, List<String> errors){
        return !validateParamIdIsNumeric(param, errors);
    }
}
