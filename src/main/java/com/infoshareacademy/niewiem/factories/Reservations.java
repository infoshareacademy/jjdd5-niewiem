package com.infoshareacademy.niewiem.factories;

import com.infoshareacademy.niewiem.Hall;
import com.infoshareacademy.niewiem.Reservation;
import com.infoshareacademy.niewiem.Table;

import java.time.LocalDateTime;

public class Reservations {
    public static boolean create(Hall hall, Table table, LocalDateTime startDateTime, Integer timeSpan, String customer) {
        return load(hall, table, startDateTime, timeSpan, customer);
        // todo: save reservation to file
    }

    public static boolean load(Hall hall, Table table, LocalDateTime startDateTime, Integer timeSpan, String customer) {
        LocalDateTime endDateTime = startDateTime.plusMinutes(timeSpan);
        return load(hall, table, startDateTime, endDateTime, customer);
    }

    public static boolean load(Hall hall, Table table, LocalDateTime startDateTime, LocalDateTime endDateTime, String customer) {
        if (isTimeSpanAvailable(hall, table, startDateTime, endDateTime)) {
            Reservation reservation = new Reservation(table, startDateTime, endDateTime, customer);
            hall.getReservations().add(reservation);
            return true;
        }
        return false;
    }

    public static boolean isTimeSpanAvailable(Hall hall, Table table, LocalDateTime startTime, LocalDateTime endTime) {
        Long findConflicts = hall.getReservations().stream()
                .filter(r -> r.getTable().equals(table))
                .filter(r -> {
                    boolean isBefore = r.getEndTime().isBefore(startTime);
                    boolean isAfter = r.getStartTime().isAfter(endTime);
                    return !(isBefore || isAfter);
                })
                .count();
        return findConflicts == 0;
    }
}
