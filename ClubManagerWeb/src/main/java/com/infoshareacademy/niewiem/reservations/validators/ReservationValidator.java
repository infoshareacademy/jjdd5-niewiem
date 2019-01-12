package com.infoshareacademy.niewiem.reservations.validators;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.reservations.enums.Period;
import com.infoshareacademy.niewiem.reservations.services.ReservationQueryService;
import com.infoshareacademy.niewiem.shared.validators.GenericValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Stateless
public class ReservationValidator extends GenericValidator {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationValidator.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final String DATE_REGEX_PATTERN = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
    private static final String TIME_REGEX_PATTERN = "[0-9]{2}:[0-9]{2}";
    private static final String TIME_ENCODED_REGEX_PATTERN = "[0-9]{2}%3A[0-9]{2}";

    @Inject
    private ReservationQueryService reservationQueryService;

    // todo: test me mofo!!
    public boolean validateRidParam(String ridParam, List<String> errors, HallDTO hallDTO) {

        if(StringUtils.isEmpty(ridParam)){
            LOG.info("No reservation id parameter in request");
            return false;
        }
        if(validateIsNotNumeric(ridParam, "reservation ID", errors)){
            return false;
        }

        Long rid = Long.parseLong(ridParam);
        if(validateResIdDoesNotExist(rid, errors)){
            return false;
        }
        if(validateTableIdDoesNotExistInActiveHallId(rid, hallDTO.getId(), errors)){
            return false;
        }
        return true;
    }

    private boolean validateTableIdExistsInActiveHallId(Long rid, Integer activeHid, List<String> errors) {
        Integer resHid = reservationQueryService.findById(rid).getTable().getHall().getId();
        if (resHid.equals(activeHid)) {
            LOG.debug("Reservations's hall matches active hall. Reservation hid: {}, Active hid: {}", resHid, activeHid);
            return true;
        }
        LOG.warn("Reservation's hall does not matches active hall. Reservation hid: {}, Active hid: {}", resHid, activeHid);
        errors.add("Requested reservation was not found in the active hall.");
        return false;
    }

    private boolean validateTableIdDoesNotExistInActiveHallId(Long rid, Integer activeHid, List<String> errors) {
        return !validateTableIdExistsInActiveHallId(rid, activeHid, errors);
    }

    boolean validateResIdDoesNotExist(Long rid, List<String> errors) {
        return !validateResIdExists(rid, errors);
    }

    boolean validateResIdExists(Long rid, List<String> errors) {
        if(reservationQueryService.doesExist(rid)){
            LOG.debug("Reservation ID was found in database. ({})", rid);
            return true;
        }
        LOG.warn("Reservation ID not found in database. ({})", rid);
        errors.add("Requested reservation as not found in database. Adding a new one.");
        return false;
    }

    public boolean validatePeriodParam(String periodParam, List<String> errors) {
        if (StringUtils.isEmpty(periodParam)) {
            LOG.info("No period parameter in request.");
            return false;
        }

        return periodExists(periodParam, errors);
    }

    public LocalDateTime returnValidatedDateTimeOrDefault(String dateParam, String timeParam, List<String> warnings, LocalDateTime defaultDateTime) {
        if (validateDateParam(dateParam)) {
            LocalDate ld = LocalDate.parse(dateParam, DATE_FORMAT);
            LocalTime lt = returnValidatedTimeOrDefault(timeParam, warnings, defaultDateTime.toLocalTime());
            return LocalDateTime.of(ld, lt);
        } else {
            warnings.add("Invalid date parameter, showing results with default value.");
            LOG.warn("Returning default date parameter: {}", defaultDateTime);
            return defaultDateTime;
        }
    }

    LocalTime returnValidatedTimeOrDefault(String timeParam, List<String> warnings, LocalTime defaultTime) {
        if (validateTimeParam(timeParam)) {
            timeParam = returnDecodedTimeParam(timeParam);
            return LocalTime.parse(timeParam, TIME_FORMAT).withSecond(defaultTime.getSecond()).withNano(defaultTime.getNano());
        } else {
            warnings.add("Invalid time parameter, showing results with default value.");
            LOG.warn("Returning default time parameter: {}. Original: {}", defaultTime, timeParam);
            return defaultTime;
        }
    }

    boolean validateDateParam(String dateParam) {
        if (StringUtils.isEmpty(dateParam)) {
            LOG.warn("No date parameter in request.");
            return false;
        }
        return dateParam.matches(DATE_REGEX_PATTERN);
    }

    String returnDecodedTimeParam(String timeParam) {
        if (timeParam.matches(TIME_ENCODED_REGEX_PATTERN)) {
            LOG.info("Found encoded character in time pattern, replacing with decoded character. From {}", timeParam);
            return timeParam.replace("%3A", ":");
        }
        return timeParam;
    }

    boolean validateTimeParam(String timeParam) {
        if (StringUtils.isEmpty(timeParam)) {
            LOG.warn("No time parameter in request.");
            return false;
        }
        return timeParam.matches(TIME_REGEX_PATTERN) || timeParam.matches(TIME_ENCODED_REGEX_PATTERN);
    }

    public LocalDateTime validateEndIsAfterStartOrReturnMax(LocalDateTime start, LocalDateTime end, List<String> warnings) {
        if (end.isBefore(start)) {
            warnings.add("The end of requested period was before the start. Showing results with no end date.");
            LOG.warn("Requested end was before start, setting it to MAX value.");
            LOG.warn("Requested start: {}", start);
            LOG.warn("Requested end: {}", end);
            return LocalDateTime.MAX;
        } else {
            LOG.debug("Requsted end was after start.");
            LOG.debug("Requested start: {}", start);
            LOG.debug("Requested end: {}", end);
            return end;
        }
    }

    boolean periodExists(String periodParam, List<String> errors) {
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
