package com.infoshareacademy.niewiem.reservations.validators;

import com.infoshareacademy.niewiem.reservations.enums.Period;
import com.infoshareacademy.niewiem.shared.validators.GenericValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ReservationValidator extends GenericValidator {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationValidator.class);

    public boolean validatePeriodParam(String periodParam, List<String> errors) {
        if (StringUtils.isEmpty(periodParam)) {
            LOG.info("No period parameter in request.");
            return false;
        }

        return validatePeriodExists(periodParam, errors);
    }

    private boolean validatePeriodExists(String periodParam, List<String> errors) {
        for (Period p : Period.values()) {
            if (p.name().equalsIgnoreCase(periodParam)) {
                LOG.debug("Period option exists. ({})", periodParam);
                return true;
            }
        }
        LOG.warn("Requested period option does not exist. ({})", periodParam);
        errors.add("Requested period option does not exist.");
        return false;
    }
}
