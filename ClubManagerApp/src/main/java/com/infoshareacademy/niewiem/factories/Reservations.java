package com.infoshareacademy.niewiem.factories;

import com.infoshareacademy.niewiem.DataProvider;
import com.infoshareacademy.niewiem.Hall;
import com.infoshareacademy.niewiem.Reservation;
import com.infoshareacademy.niewiem.Table;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class Reservations {
    public static Optional<Reservation> create(Hall hall, Table table, LocalDateTime startDateTime, Integer timeSpan, String customer) {
        Optional<Reservation> reservationOpt = load(hall, table, startDateTime, timeSpan, customer);
        boolean reservationWasCreated = reservationOpt.isPresent();

        if (reservationWasCreated) {
            Reservation reservation = reservationOpt.get();
            DataProvider.saveReservationInCsv(reservation);
        }
        return reservationOpt;
    }

    public static Optional<Reservation> load(Hall hall, Table table, LocalDateTime startDateTime, Integer timeSpan, String customer) {
        LocalDateTime endDateTime = startDateTime.plusMinutes(timeSpan);
        return load(hall, table, startDateTime, endDateTime, customer);
    }

    public static Optional<Reservation> load(Hall hall, Table table, LocalDateTime startDateTime, LocalDateTime endDateTime, String customer) {
        if (isTimeSpanAvailable(hall, table, startDateTime, endDateTime)) {
            Reservation reservation = new Reservation(table, startDateTime, endDateTime, customer);
            hall.getReservations().add(reservation);
            return Optional.of(reservation);
        }
        return Optional.empty();
    }

    public static void stop(Hall hall, Table table) {
        hall.getReservations().stream()
                .filter(r -> r.getTable().equals(table))
                .filter(Reservation::isInProgress)
                .forEach(r -> r.setEndTime(LocalDateTime.now()));
        //todo: find record for the reservation and change history
    }

    public static void stop(Hall hall, Reservation reservation) {
        reservation.setEndTime(LocalDateTime.now());
        //todo: find record for the reservation and change history
    }

    public static void cancelAllFutureReservationsFromTable(Hall hall, Table table) {
        List<Reservation> resToDelete = hall.getReservations().stream()
                .filter(r -> r.getTable().equals(table))
                .filter(Reservation::isUpcoming)
                .collect(Collectors.toList());
        for (Reservation res : resToDelete){
            hall.getReservations().remove(res);
        }
        // todo: remove from file
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

    public static boolean tableIsActive(Hall hall, Table table) {
        return hall.getReservations().stream()
                .filter(r -> r.getTable().equals(table))
                .anyMatch(Reservation::isInProgress);
    }

    public static List<Reservation> getUpcomingOrInProgressReservations(Hall hall) {
        return hall.getReservations().stream()
                .filter(Reservation::isUpcomingOrInProgress)
                .collect(Collectors.toList());
    }

    public static List<Reservation> getUpcomingReservations(Hall hall) {
        return hall.getReservations().stream()
                .filter(Reservation::isUpcoming)
                .collect(Collectors.toList());
    }

    public static void cancel(Hall hall, Reservation reservation) {
        hall.getReservations().remove(reservation);
        //todo: find record for the reservation and delete
    }

    public static Map<Table, Long> getAllTablesAndRemainingTimes(Hall hall) {
        Map<Table, Long> activeTables = getActiveTablesAndRemainingTimes(hall);
        return hall.getTableList().stream()
                .collect(toMap(
                        t -> t,
                        t -> activeTables.getOrDefault(t, 0L)
                ));
    }

    public static Map<Table, Long> getActiveTablesAndRemainingTimes(Hall hall) {
        return hall.getReservations().stream()
                .filter(Reservation::isInProgress)
                .collect(toMap(
                        Reservation::getTable,
                        Reservation::getTimeRemainingInSeconds
                ));
    }

    public static List<Reservation> getPastReservations(Hall hall) {
        return hall.getReservations().stream()
                .filter(Reservation::isOver)
                .collect(Collectors.toList());
    }
}
