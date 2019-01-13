package com.infoshareacademy.niewiem.reservations.publishers;

import com.infoshareacademy.niewiem.halls.dto.HallDTO;
import com.infoshareacademy.niewiem.reservations.dto.ReservationInMillisDTO;
import com.infoshareacademy.niewiem.reservations.services.ReservationQueryService;
import com.infoshareacademy.niewiem.reservations.validators.ReservationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Stateless
public class ReservationPublisher {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationPublisher.class);

    @Inject
    private ReservationQueryService reservationQueryService;

    @Inject
    private ReservationValidator reservationValidator;

    public void publishReservationById(Map<String, Object> model, List<String> errors, String ridParam, HallDTO hallDTO) {
        if(reservationValidator.validateRidParam(ridParam, errors, hallDTO)){
            Long rid = Long.parseLong(ridParam);
            ReservationInMillisDTO reservation = reservationQueryService.findById(rid);
            model.put("reservation", reservation);
        }
    }
}
