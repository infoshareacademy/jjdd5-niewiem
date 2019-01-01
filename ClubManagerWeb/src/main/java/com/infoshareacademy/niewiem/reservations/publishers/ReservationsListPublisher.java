package com.infoshareacademy.niewiem.reservations.publishers;

import com.infoshareacademy.niewiem.enums.TableType;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.services.ActiveHallService;
import com.infoshareacademy.niewiem.reservations.dto.ReservationInMillisDTO;
import com.infoshareacademy.niewiem.reservations.enums.Period;
import com.infoshareacademy.niewiem.reservations.mappers.PeriodMapper;
import com.infoshareacademy.niewiem.reservations.services.ReservationQueryService;
import com.infoshareacademy.niewiem.reservations.validators.ReservationValidator;
import com.infoshareacademy.niewiem.tables.validators.TableValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Stateless
public class ReservationsListPublisher {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationsListPublisher.class);

    private static final String TABLE_ID_PARAM = "tid";
    private static final String TABLE_TYPE_PARAM = "type";
    private static final String PERIOD_PARAM = "period";

    @Inject
    private ActiveHallService activeHallService;

    @Inject
    private TableValidator tableValidator;

    @Inject
    private PeriodMapper periodMapper;

    @Inject
    private ReservationValidator reservationValidator;

    @Inject
    private ReservationQueryService reservationQueryService;

    public void publishRequestedReservations(Map<String, Object> model, List<String> errors, HttpServletRequest req) {
        HallDTO hallDTO = activeHallService.getActiveHall(req.getSession());

        if (reservationValidator.validatePeriodParam(req.getParameter(PERIOD_PARAM), errors)) {
            publishOptionsWhenPeriodParamExists(model, errors, req, hallDTO);
        } else {
            if (tableValidator.validateTidParam(req.getParameter(TABLE_ID_PARAM), errors, hallDTO)) {
                Integer tid = Integer.parseInt(req.getParameter(TABLE_ID_PARAM));
                publishReservationsByTable(model, tid);
            } else if (tableValidator.validateTypeParam(req.getParameter(TABLE_TYPE_PARAM), errors)) {
                TableType type = TableType.valueOf(req.getParameter(TABLE_TYPE_PARAM).toUpperCase());
                publishReservationsByHallAndType(model, hallDTO, type);
            } else {
                // todo: the default could be set in settings in admin panel - should it be ALL? Should it be TODAY?.
                publishReservationsByHallAndPeriodInclusive(model, hallDTO, Period.TODAY);
            }
        }
    }

    private void publishOptionsWhenPeriodParamExists(Map<String, Object> model, List<String> errors, HttpServletRequest req, HallDTO hallDTO) {
        Period period = Period.valueOf(req.getParameter(PERIOD_PARAM).toUpperCase());
        switch (period) {
            // todo: case for CUSTOM
//            case CUSTOM:
//                publishCustomPeriodOptions(model, errors, req, hallDTO, period);
//                break;
            case ALL:
                publishReservationsByHall(model, hallDTO);
                break;
            case ACTIVE:
                publishActiveReservationsByHall(model, hallDTO);
                break;
            case TODAY:
            case YESTERDAY:
            case TOMORROW:
            case LAST24H:
            case THIS_MONTH:
                publishInclusivePeriodOptions(model, errors, req, hallDTO, period);
                break;
            case HISTORY:
            case UPCOMING:
                publishExclusivePeriodOptions(model, errors, req, hallDTO, period);
                break;
        }
    }

    private void publishActiveReservationsByHall(Map<String, Object> model, HallDTO hallDTO) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findActiveByHall(hallDTO);
        model.put("reservations", reservations);
    }

    private void publishInclusivePeriodOptions(Map<String, Object> model, List<String> errors, HttpServletRequest req, HallDTO hallDTO, Period period) {
        if (tableValidator.validateTidParam(req.getParameter(TABLE_ID_PARAM), errors, hallDTO)) {
            Integer tid = Integer.parseInt(req.getParameter(TABLE_ID_PARAM));
            publishReservationsByTableAndPeriodInclusive(model, tid, period);
        } else if (tableValidator.validateTypeParam(req.getParameter(TABLE_TYPE_PARAM), errors)) {
            TableType type = TableType.valueOf(req.getParameter(TABLE_TYPE_PARAM).toUpperCase());
            publishReservationsByHallAndTypeAndPeriodInclusive(model, hallDTO, type, period);
        } else {
            publishReservationsByHallAndPeriodInclusive(model, hallDTO, period);
        }
    }

    private void publishExclusivePeriodOptions(Map<String, Object> model, List<String> errors, HttpServletRequest req, HallDTO hallDTO, Period period) {
        if (tableValidator.validateTidParam(req.getParameter(TABLE_ID_PARAM), errors, hallDTO)) {
            Integer tid = Integer.parseInt(req.getParameter(TABLE_ID_PARAM));
            publishReservationsByTableAndPeriodExclusive(model, tid, period);
        } else if (tableValidator.validateTypeParam(req.getParameter(TABLE_TYPE_PARAM), errors)) {
            TableType type = TableType.valueOf(req.getParameter(TABLE_TYPE_PARAM).toUpperCase());
            publishReservationsByHallAndTypeAndPeriodExclusive(model, hallDTO, type, period);
        } else {
            publishReservationsByHallAndPeriodExclusive(model, hallDTO, period);
        }
    }

    private void publishReservationsByHall(Map<String, Object> model, HallDTO hallDTO) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByHall(hallDTO);
        model.put("reservations", reservations);
    }

    private void publishReservationsByTable(Map<String, Object> model, Integer tid) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByTableId(tid);
        model.put("reservations", reservations);
    }

    private void publishReservationsByHallAndType(Map<String, Object> model, HallDTO hallDTO, TableType type) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByHallAndType(hallDTO, type);
        model.put("reservations", reservations);
    }

    private void publishReservationsByHallAndPeriodInclusive(Map<String, Object> model, HallDTO hallDTO, Period period) {
        LocalDateTime start = periodMapper.getStartTime(period);
        LocalDateTime end = periodMapper.getEndTime(period);
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByHallAndTimeSpanInclusive(hallDTO, start, end);
        model.put("reservations", reservations);
    }

    private void publishReservationsByTableAndPeriodInclusive(Map<String, Object> model, Integer tid, Period period) {
        LocalDateTime start = periodMapper.getStartTime(period);
        LocalDateTime end = periodMapper.getEndTime(period);
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByTableIdAndTimeSpanInclusive(tid, start, end);
        model.put("reservations", reservations);
    }

    private void publishReservationsByHallAndTypeAndPeriodInclusive(Map<String, Object> model, HallDTO hallDTO, TableType type, Period period) {
        LocalDateTime start = periodMapper.getStartTime(period);
        LocalDateTime end = periodMapper.getEndTime(period);
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByHallAndTypeAndTimeSpanInclusive(hallDTO, type, start, end);
        model.put("reservations", reservations);
    }

    private void publishReservationsByHallAndPeriodExclusive(Map<String, Object> model, HallDTO hallDTO, Period period) {
        LocalDateTime start = periodMapper.getStartTime(period);
        LocalDateTime end = periodMapper.getEndTime(period);
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByHallAndTimeSpanExclusive(hallDTO, start, end);
        model.put("reservations", reservations);
    }

    private void publishReservationsByTableAndPeriodExclusive(Map<String, Object> model, Integer tid, Period period) {
        LocalDateTime start = periodMapper.getStartTime(period);
        LocalDateTime end = periodMapper.getEndTime(period);
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByTableIdAndTimeSpanExclusive(tid, start, end);
        model.put("reservations", reservations);
    }

    private void publishReservationsByHallAndTypeAndPeriodExclusive(Map<String, Object> model, HallDTO hallDTO, TableType type, Period period) {
        LocalDateTime start = periodMapper.getStartTime(period);
        LocalDateTime end = periodMapper.getEndTime(period);
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByHallAndTypeAndTimeSpanExclusive(hallDTO, type, start, end);
        model.put("reservations", reservations);
    }
}