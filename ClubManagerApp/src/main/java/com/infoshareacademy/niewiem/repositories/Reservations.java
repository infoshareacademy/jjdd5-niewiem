package com.infoshareacademy.niewiem.repositories;

import com.infoshareacademy.niewiem.dao.DataProvider;
import com.infoshareacademy.niewiem.pojo.Hall;
import com.infoshareacademy.niewiem.pojo.Reservation;
import com.infoshareacademy.niewiem.pojo.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class Reservations {
    private static List<Reservation> reservations = new ArrayList<>();


    public static List<Reservation> getReservations() {
        return reservations;
    }

    public static void setReservations(List<Reservation> reservations) {
        Reservations.reservations = reservations;
    }

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
            reservations.add(reservation);
            return Optional.of(reservation);
        }
        return Optional.empty();
    }

    public static void stop(Hall hall, Table table) {
        reservations.stream()
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
        List<Reservation> resToDelete = reservations.stream()
                .filter(r -> r.getTable().equals(table))
                .filter(Reservation::isUpcoming)
                .collect(Collectors.toList());
        for (Reservation res : resToDelete){
            reservations.remove(res);
        }
        // todo: remove from file
    }

    public static boolean isTimeSpanAvailable(Hall hall, Table table, LocalDateTime startTime, LocalDateTime endTime) {
        Long findConflicts = reservations.stream()
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
        return reservations.stream()
                .filter(r -> r.getTable().equals(table))
                .anyMatch(Reservation::isInProgress);
    }

    public static List<Reservation> getUpcomingOrInProgressReservations(Hall hall) {
        return reservations.stream()
                .filter(Reservation::isUpcomingOrInProgress)
                .collect(Collectors.toList());
    }

    public static List<Reservation> getUpcomingReservations(Hall hall) {
        return reservations.stream()
                .filter(Reservation::isUpcoming)
                .collect(Collectors.toList());
    }

    public static void cancel(Hall hall, Reservation reservation) {
        reservations.remove(reservation);
        //todo: find record for the reservation and delete
    }

    public static Map<Table, Long> getAllTablesAndRemainingTimes(Hall hall) {
        Map<Table, Long> activeTables = getActiveTablesAndRemainingTimes(hall);
        return Tables.getTables().stream()
                .collect(toMap(
                        t -> t,
                        t -> activeTables.getOrDefault(t, 0L)
                ));
    }

    public static Map<Table, Long> getActiveTablesAndRemainingTimes(Hall hall) {
        return reservations.stream()
                .filter(Reservation::isInProgress)
                .collect(toMap(
                        Reservation::getTable,
                        Reservation::getTimeRemainingInSeconds
                ));
    }

    public static List<Reservation> getPastReservations(Hall hall) {
        return reservations.stream()
                .filter(Reservation::isOver)
                .collect(Collectors.toList());
    }
}
