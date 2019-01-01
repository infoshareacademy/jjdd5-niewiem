package com.infoshareacademy.niewiem.reservations.mappers;

import com.infoshareacademy.niewiem.reservations.enums.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Stateless
public class PeriodMapper {
    private static final Logger LOG = LoggerFactory.getLogger(PeriodMapper.class);

    public LocalDateTime getNow() {
        // Separated into class to make it mock-able in tests.
        return LocalDateTime.now();
    }

    public LocalDateTime getStartTime(Period period) {
        LocalDateTime result;
        LocalDateTime now = getNow();
        switch (period) {
            case YESTERDAY:
                result = now.minusDays(1).with(LocalTime.MIN);
                break;
            case TODAY:
                result = now.with(LocalTime.MIN);
                break;
            case TOMORROW:
                result = now.plusDays(1).with(LocalTime.MIN);
                break;
            case LAST24H:
                result = now.minusHours(24);
                break;
            case THIS_MONTH:
                result = now.withDayOfMonth(1).with(LocalTime.MIN);
                break;
            case HISTORY:
                result = LocalDateTime.MIN;
                break;
            case UPCOMING:
                result = now;
                break;
            default:
                result = LocalDateTime.MIN;
        }
        LOG.info("Returning start date-time of: {}, for period: {}", result, period);
        return result;
    }

    public LocalDateTime getEndTime(Period period) {
        LocalDateTime result;
        LocalDateTime now = getNow();
        switch (period) {
            case YESTERDAY:
                result = now.minusDays(1).with(LocalTime.MAX);
                break;
            case TODAY:
                result = now.with(LocalTime.MAX);
                break;
            case TOMORROW:
                result = now.plusDays(1).with(LocalTime.MAX);
                break;
            case LAST24H:
                result = now;
                break;
            case THIS_MONTH:
                result = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).with(LocalTime.MAX);
                break;
            case HISTORY:
                result = now;
                break;
            case UPCOMING:
                result = LocalDateTime.MAX;
                break;
            default:
                result = LocalDateTime.MAX;
                break;
        }
        LOG.info("Returning end date-time of: {}, for period: {}", result, period);
        return result;
    }
}