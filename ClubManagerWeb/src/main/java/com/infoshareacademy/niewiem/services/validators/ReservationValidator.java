package com.infoshareacademy.niewiem.services.validators;

import com.infoshareacademy.niewiem.pojo.Reservation;

public class ReservationValidator {

    public boolean isResIdNotNull(Reservation reservation){
        return reservation.getId() != null;
    }

    public boolean isIdNotNull(Long id){
        return id != null;
    }

    public boolean isStartTimeNotNull(Reservation reservation){
        return reservation.getStartTime() != null;
    }

    public boolean isEndTimeNotNull(Reservation reservation){
        return reservation.getEndTime() != null;
    }

    public boolean isEndAfterStartTime(Reservation reservation){
        return reservation.getEndTime().isAfter(reservation.getStartTime());
    }

}
