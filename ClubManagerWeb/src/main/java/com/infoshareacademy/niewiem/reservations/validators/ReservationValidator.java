package com.infoshareacademy.niewiem.reservations.validators;

import com.infoshareacademy.niewiem.reservations.enums.Period;
import com.infoshareacademy.niewiem.shared.validators.GenericValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ReservationValidator extends GenericValidator {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationValidator.class);

    public boolean validatePeriodExists(String periodParam, List<String> errors) {
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
