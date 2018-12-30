package com.infoshareacademy.niewiem.reservations.publishers;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.halls.services.ActiveHallService;
import com.infoshareacademy.niewiem.reservations.dto.ReservationInMillisDTO;
import com.infoshareacademy.niewiem.reservations.services.ReservationQueryService;
import com.infoshareacademy.niewiem.tables.validators.TableValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Stateless
public class ReservationsListPublisher {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationsListPublisher.class);

    @Inject
    private ActiveHallService activeHallService;

    @Inject
    private TableValidator tableValidator;

    @Inject
    private ReservationQueryService reservationQueryService;

    public void publishRequestedReservations(Map<String, Object> model, List<String> errors, HttpServletRequest req){
        HallDTO hallDTO = activeHallService.getActiveHall(req.getSession());

        if(tidParamExists(req, errors, hallDTO)){
            Integer tid = Integer.parseInt(req.getParameter("tid"));
            publishReservationsByTable(model, tid);
        }else{
            publishReservationsByHall(model, hallDTO);
        }
    }

    private boolean tidParamExists(HttpServletRequest req, List<String> errors, HallDTO hallDTO) {
        String tidParam = req.getParameter("tid");
        if (StringUtils.isEmpty(tidParam)) {
            LOG.info("No table id parameter in request.");
            return false;
        }
        if (tableValidator.validateIsNotNumeric(tidParam, "table ID" , errors)) {
            return false;
        }

        Integer tid = Integer.parseInt(tidParam);
        if (tableValidator.validateTableIdDoesNotExists(tid, errors)) {
            return false;
        }
        if (tableValidator.validateTableIdDoesNotExistInActiveHallId(tid, hallDTO.getId(), errors)) {
            return false;
        }

        return true;
    }

    private void publishReservationsByHall(Map<String, Object> model, HallDTO hallDTO) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findAllByHall(hallDTO);
        model.put("reservations", reservations);
    }

    private void publishReservationsByTable(Map<String, Object> model, Integer tid) {
        List<ReservationInMillisDTO> reservations = reservationQueryService.findAllByTableId(tid);
        model.put("reservations", reservations);
    }
}
