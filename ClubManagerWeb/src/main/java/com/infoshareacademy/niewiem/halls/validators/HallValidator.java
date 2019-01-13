package com.infoshareacademy.niewiem.halls.validators;

import com.infoshareacademy.niewiem.halls.dao.HallDao;
import com.infoshareacademy.niewiem.shared.validators.GenericValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class HallValidator extends GenericValidator {
    private static final Logger LOG = LoggerFactory.getLogger(HallValidator.class);

    @Inject
    private HallDao hallDao;

    public boolean validateHallIdExists(Integer hid) {
        if (hallDao.doesExist(hid)) {
            LOG.debug("Hall ID was found in database. ({})", hid);
            return true;
        }
        LOG.warn("Hall ID not found in database. ({})", hid);
        return false;
    }

    public boolean validateHallIdDoesNotExists(Integer hid) {
        return !validateHallIdExists(hid);
    }

    public boolean validateHidParam(String hidParam, List<String> errors) {
        if(StringUtils.isEmpty(hidParam)){
            LOG.info("No hall id parameter in request");
            return false;
        }
        if(validateIsNotNumeric(hidParam, "hall ID", errors)){
            return false;
        }

        Integer hid = Integer.parseInt(hidParam);
        return validateHallIdExists(hid);
    }
}
