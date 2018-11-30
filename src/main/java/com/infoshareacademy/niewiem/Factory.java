package com.infoshareacademy.niewiem;

import com.infoshareacademy.niewiem.Reservation;
import com.infoshareacademy.niewiem.Table;

import java.time.LocalDateTime;

public class Factory {

    public static Table createNewTable(String name, String tableID){
        return null;
    }

    public static Table loadTable(){
        return null;
    }

    public static Reservation createNewReservation(Table table, LocalDateTime startTime, LocalDateTime stopTime, String comment){
       return null;
    }

    public static Reservation createNewReservation(Table table, LocalDateTime startTime, LocalDateTime stopTime){
        return null;
    }

    public static Reservation loadReservation(){
        return null;
    }
}
