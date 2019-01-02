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
    private static final String START_DATE_PARAM = "startDate";
    private static final String START_TIME_PARAM = "startTime";
    private static final String END_DATE_PARAM = "endDate";
    private static final String END_TIME_PARAM = "endTime";

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

    public void publishRequestedReservations(Map<String, Object> model, List<String> errors, List<String> warnings, HttpServletRequest req) {
        HallDTO hallDTO = activeHallService.getActiveHall(req.getSession());

        if (reservationValidator.validatePeriodParam(req.getParameter(PERIOD_PARAM), errors)) {
            publishOptionsWhenPeriodParamExists(model, errors, warnings, req, hallDTO);
        } else {
            if (tableValidator.validateTidParam(req.getParameter(TABLE_ID_PARAM), errors, hallDTO)) {
                Integer tid = Integer.parseInt(req.getParameter(TABLE_ID_PARAM));
                publishReservationsByTable(model, tid);
            } else if (tableValidator.validateTypeParam(req.getParameter(TABLE_TYPE_PARAM), errors)) {
                TableType type = TableType.valueOf(req.getParameter(TABLE_TYPE_PARAM).toUpperCase());
                publishReservationsByHallAndType(model, hallDTO, type);
            } else {
                // todo: the default could be set in settings in admin panel - should it be ALL? Should it be TODAY?.
                Period period = Period.TODAY;
                LocalDateTime start = periodMapper.getStartTime(period);
                LocalDateTime end = periodMapper.getEndTime(period);
                publishReservationsByHallAndPeriodInclusive(model, hallDTO, start, end);
            }
        }
    }

    private void publishOptionsWhenPeriodParamExists(Map<String, Object> model, List<String> errors, List<String> warnings, HttpServletRequest req, HallDTO hallDTO) {
        Period period = Period.valueOf(req.getParameter(PERIOD_PARAM).toUpperCase());
        LocalDateTime start;
        LocalDateTime end;
        switch (period) {
            case CUSTOM:
                start = reservationValidator.returnValidatedDateTimeOrDefault(
                        req.getParameter(START_DATE_PARAM),
                        req.getParameter(START_TIME_PARAM),
                        warnings,
                        LocalDateTime.MIN
                );
                end = reservationValidator.returnValidatedDateTimeOrDefault(
                        req.getParameter(END_DATE_PARAM),
                        req.getParameter(END_TIME_PARAM),
                        warnings,
                        LocalDateTime.MAX
                );
                reservationValidator.validateEndIsAfterStartOrReturnMax(start, end, warnings);

                publishInclusivePeriodOptions(model, errors, req, hallDTO, start, end);
                break;
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
                start = periodMapper.getStartTime(period);
                end = periodMapper.getEndTime(period);
                publishInclusivePeriodOptions(model, errors, req, hallDTO, start, end);
                break;
            case HISTORY:
            case UPCOMING:
                start = periodMapper.getStartTime(period);
                end = periodMapper.getEndTime(period);
                publishExclusivePeriodOptions(model, errors, req, hallDTO, start, end);
                break;
        }
    }

    private void publishActiveReservationsByHall(Map<String, Object> model, HallDTO hallDTO) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findActiveByHall(hallDTO);
        model.put("reservations", reservations);
    }

    private void publishInclusivePeriodOptions(Map<String, Object> model, List<String> errors, HttpServletRequest req, HallDTO hallDTO, LocalDateTime start, LocalDateTime end) {
        if (tableValidator.validateTidParam(req.getParameter(TABLE_ID_PARAM), errors, hallDTO)) {
            Integer tid = Integer.parseInt(req.getParameter(TABLE_ID_PARAM));
            publishReservationsByTableAndPeriodInclusive(model, tid, start, end);
        } else if (tableValidator.validateTypeParam(req.getParameter(TABLE_TYPE_PARAM), errors)) {
            TableType type = TableType.valueOf(req.getParameter(TABLE_TYPE_PARAM).toUpperCase());
            publishReservationsByHallAndTypeAndPeriodInclusive(model, hallDTO, type, start, end);
        } else {
            publishReservationsByHallAndPeriodInclusive(model, hallDTO, start, end);
        }
    }

    private void publishExclusivePeriodOptions(Map<String, Object> model, List<String> errors, HttpServletRequest req, HallDTO hallDTO, LocalDateTime start, LocalDateTime end) {
        if (tableValidator.validateTidParam(req.getParameter(TABLE_ID_PARAM), errors, hallDTO)) {
            Integer tid = Integer.parseInt(req.getParameter(TABLE_ID_PARAM));
            publishReservationsByTableAndPeriodExclusive(model, tid, start, end);
        } else if (tableValidator.validateTypeParam(req.getParameter(TABLE_TYPE_PARAM), errors)) {
            TableType type = TableType.valueOf(req.getParameter(TABLE_TYPE_PARAM).toUpperCase());
            publishReservationsByHallAndTypeAndPeriodExclusive(model, hallDTO, type, start, end);
        } else {
            publishReservationsByHallAndPeriodExclusive(model, hallDTO, start, end);
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

    private void publishReservationsByHallAndPeriodInclusive(Map<String, Object> model, HallDTO hallDTO, LocalDateTime start, LocalDateTime end) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByHallAndTimeSpanInclusive(hallDTO, start, end);
        model.put("reservations", reservations);
    }

    private void publishReservationsByTableAndPeriodInclusive(Map<String, Object> model, Integer tid, LocalDateTime start, LocalDateTime end) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByTableIdAndTimeSpanInclusive(tid, start, end);
        model.put("reservations", reservations);
    }

    private void publishReservationsByHallAndTypeAndPeriodInclusive(Map<String, Object> model, HallDTO hallDTO, TableType type, LocalDateTime start, LocalDateTime end) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByHallAndTypeAndTimeSpanInclusive(hallDTO, type, start, end);
        model.put("reservations", reservations);
    }

    private void publishReservationsByHallAndPeriodExclusive(Map<String, Object> model, HallDTO hallDTO, LocalDateTime start, LocalDateTime end) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByHallAndTimeSpanExclusive(hallDTO, start, end);
        model.put("reservations", reservations);
    }

    private void publishReservationsByTableAndPeriodExclusive(Map<String, Object> model, Integer tid, LocalDateTime start, LocalDateTime end) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByTableIdAndTimeSpanExclusive(tid, start, end);
        model.put("reservations", reservations);
    }

    private void publishReservationsByHallAndTypeAndPeriodExclusive(Map<String, Object> model, HallDTO hallDTO, TableType type, LocalDateTime start, LocalDateTime end) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByHallAndTypeAndTimeSpanExclusive(hallDTO, type, start, end);
        model.put("reservations", reservations);
    }
}