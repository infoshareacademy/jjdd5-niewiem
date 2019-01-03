package com.infoshareacademy.niewiem.reservations.publishers;

import com.infoshareacademy.niewiem.enums.TableType;
import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.services.ActiveHallService;
import com.infoshareacademy.niewiem.reservations.dto.ReservationInMillisDTO;
import com.infoshareacademy.niewiem.reservations.enums.Exclusivity;
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

import static com.infoshareacademy.niewiem.reservations.enums.Exclusivity.EXCLUSIVE;
import static com.infoshareacademy.niewiem.reservations.enums.Exclusivity.INCLUSIVE;

@Stateless
public class ReservationsListPublisher {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationsListPublisher.class);

    public static final String RESERVATIONS_IN_MODEL = "reservations";

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
            publishOptionsWhenPeriodParamDoesNotExist(model, errors, req, hallDTO);
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

                publishReservationsByTimeSpan(model, errors, req, hallDTO, start, end, INCLUSIVE);
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
                publishReservationsByTimeSpan(model, errors, req, hallDTO, start, end, INCLUSIVE);
                break;
            case HISTORY:
            case UPCOMING:
                start = periodMapper.getStartTime(period);
                end = periodMapper.getEndTime(period);
                publishReservationsByTimeSpan(model, errors, req, hallDTO, start, end, EXCLUSIVE);
                break;
            default:
                start = periodMapper.getStartTime(Period.TODAY);
                end = periodMapper.getEndTime(Period.TODAY);
                publishReservationsByTimeSpan(model, errors, req, hallDTO, start, end, INCLUSIVE);
                break;
        }
    }

    private void publishOptionsWhenPeriodParamDoesNotExist(Map<String, Object> model, List<String> errors, HttpServletRequest req, HallDTO hallDTO) {
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
            publishReservationsByHallAndPeriod(model, hallDTO, start, end, INCLUSIVE);
        }
    }

    private void publishReservationsByTimeSpan(Map<String, Object> model, List<String> errors, HttpServletRequest req, HallDTO hallDTO, LocalDateTime start, LocalDateTime end, Exclusivity exclusivity) {
        if (tableValidator.validateTidParam(req.getParameter(TABLE_ID_PARAM), errors, hallDTO)) {
            Integer tid = Integer.parseInt(req.getParameter(TABLE_ID_PARAM));
            publishReservationsByTableAndPeriod(model, tid, start, end, exclusivity);
        } else if (tableValidator.validateTypeParam(req.getParameter(TABLE_TYPE_PARAM), errors)) {
            TableType type = TableType.valueOf(req.getParameter(TABLE_TYPE_PARAM).toUpperCase());
            publishReservationsByHallAndTypeAndPeriod(model, hallDTO, type, start, end, exclusivity);
        } else {
            publishReservationsByHallAndPeriod(model, hallDTO, start, end, exclusivity);
        }
    }

    private void publishReservationsByHall(Map<String, Object> model, HallDTO hallDTO) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByHall(hallDTO);
        model.put(RESERVATIONS_IN_MODEL, reservations);
    }

    private void publishActiveReservationsByHall(Map<String, Object> model, HallDTO hallDTO) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findActiveByHall(hallDTO);
        model.put(RESERVATIONS_IN_MODEL, reservations);
    }

    private void publishReservationsByTable(Map<String, Object> model, Integer tid) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByTableId(tid);
        model.put(RESERVATIONS_IN_MODEL, reservations);
    }

    private void publishReservationsByHallAndType(Map<String, Object> model, HallDTO hallDTO, TableType type) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByHallAndType(hallDTO, type);
        model.put(RESERVATIONS_IN_MODEL, reservations);
    }

    private void publishReservationsByHallAndPeriod(Map<String, Object> model, HallDTO hallDTO, LocalDateTime start, LocalDateTime end, Exclusivity exclusivity) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByHallAndTimeSpan(hallDTO, start, end, exclusivity);
        model.put(RESERVATIONS_IN_MODEL, reservations);
    }

    private void publishReservationsByTableAndPeriod(Map<String, Object> model, Integer tid, LocalDateTime start, LocalDateTime end, Exclusivity exclusivity) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByTableIdAndTimeSpan(tid, start, end, exclusivity);
        model.put(RESERVATIONS_IN_MODEL, reservations);
    }

    private void publishReservationsByHallAndTypeAndPeriod(Map<String, Object> model, HallDTO hallDTO, TableType type, LocalDateTime start, LocalDateTime end, Exclusivity exclusivity) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findByHallAndTypeAndTimeSpan(hallDTO, type, start, end, exclusivity);
        model.put(RESERVATIONS_IN_MODEL, reservations);
    }
}