package com.infoshareacademy.niewiem.reservations.validators;

import com.infoshareacademy.niewiem.shared.validators.GenericValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;

@Stateless
public class ReservationValidator extends GenericValidator {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationValidator.class);

}
