package com.infoshareacademy.niewiem.reservations.validators;

import com.infoshareacademy.niewiem.reservations.enums.Period;
import com.infoshareacademy.niewiem.shared.validators.GenericValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
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

    public LocalTime returnValidatedTimeOrDefault(String timeParam, List<String> warnings, LocalTime defaultTime) {
        if (validateTimeParam(timeParam)) {
            timeParam = returnDecodedTimeParam(timeParam);
            return LocalTime.parse(timeParam, TIME_FORMAT).withSecond(defaultTime.getSecond()).withNano(defaultTime.getNano());
        } else {
            warnings.add("Invalid time parameter, showing results with default value.");
            LOG.warn("Returning default time parameter: {}. Original: {}", defaultTime, timeParam);
            return defaultTime;
        }
    }

    public boolean validateDateParam(String dateParam) {
        if (StringUtils.isEmpty(dateParam)) {
            LOG.warn("No date parameter in request.");
            return false;
        }
        return dateParam.matches(DATE_REGEX_PATTERN);
    }

    public String returnDecodedTimeParam(String timeParam) {
        if (timeParam.matches(TIME_ENCODED_REGEX_PATTERN)) {
            LOG.info("Found encoded character in time pattern, replacing with decoded character. From {}", timeParam);
            return timeParam.replace("%3A", ":");
        }
        return timeParam;
    }

    public boolean validateTimeParam(String timeParam) {
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

    public boolean periodExists(String periodParam, List<String> errors) {
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
