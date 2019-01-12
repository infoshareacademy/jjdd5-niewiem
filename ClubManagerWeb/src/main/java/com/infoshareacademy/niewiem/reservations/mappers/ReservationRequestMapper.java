package com.infoshareacademy.niewiem.reservations.mappers;

import com.infoshareacademy.niewiem.domain.Reservation;
import com.infoshareacademy.niewiem.domain.Table;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.reservations.validators.ReservationValidator;
import com.infoshareacademy.niewiem.tables.dao.TableDao;
import com.infoshareacademy.niewiem.tables.validators.TableValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class ReservationRequestMapper {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationRequestMapper.class);

    @Inject
    private TableDao tableDao;

    @Inject
    private ReservationValidator reservationValidator;

    @Inject
    private TableValidator tableValidator;

    public Reservation getReservationWithoutId(HttpServletRequest req, List<String> errors, HallDTO hallDTO){
        Reservation reservation = new Reservation();

        LOG.info("Requested tid {}", req.getParameter("tid"));
        LOG.info("Requested startDate {}", req.getParameter("startDate"));
        LOG.info("Requested startTime {}", req.getParameter("startTime"));
        LOG.info("Requested timeSpan {}", req.getParameter("timeSpan"));
        LOG.info("Requested customer {}", req.getParameter("customer"));
        
        Table table = getTableFromTid(req.getParameter("tid"), errors, hallDTO);
        LocalDateTime start = getStartLDT(req.getParameter("startDate"), req.getParameter("startTime"), errors);
        LocalDateTime end = getEndLDT(start, req.getParameter("timeSpan"), errors);
        String customer = req.getParameter("customer");

        if(table == null || start == LocalDateTime.MIN || end == null){
            return null;
        }

        reservation.setStartTime(start);
        reservation.setTable(table);
        reservation.setEndTime(end);
        reservation.setCustomer(customer);

        return reservation;
    }

    private LocalDateTime getEndLDT(LocalDateTime start, String timeSpan, List<String> errors) {
        if(start == LocalDateTime.MIN){
            return null;
        }
        if(StringUtils.isEmpty(timeSpan)){
            errors.add("Time span cannot be left empty.");
            LOG.info("No time span in request.");
            return null;
        }
        if(reservationValidator.validateIsNotNumeric(timeSpan, "time span", errors)){
            return null;
        }
        return start.plusMinutes(Integer.parseInt(timeSpan));
    }

    private LocalDateTime getStartLDT(String startDate, String startTime, List<String> errors) {
        return reservationValidator.returnValidatedDateTimeOrDefault(startDate, startTime, errors, LocalDateTime.MIN);
    }

    private Table getTableFromTid(String tidString, List<String> errors, HallDTO hallDTO){
        if(tableValidator.validateTidParam(tidString, errors, hallDTO)){
            Integer tid = Integer.parseInt(tidString);
            return tableDao.findById(tid);
        }
        return null;
    }
}