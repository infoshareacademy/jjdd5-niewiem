package com.infoshareacademy.niewiem.factories;

import com.infoshareacademy.niewiem.Hall;
import com.infoshareacademy.niewiem.Reservation;
import com.infoshareacademy.niewiem.Table;

import java.time.LocalDateTime;

public class Reservations {
    public static Reservation create(Hall hall, Table table, LocalDateTime startTime, LocalDateTime stopTime, String customer){
        return null;
    }

    public static Reservation create(Hall hall,  Table table, LocalDateTime startDateTime, Integer timeSpan, String customer){
        // todo: do me already
        return null;
    }

    public static Reservation load(){
        return null;
    }
}
