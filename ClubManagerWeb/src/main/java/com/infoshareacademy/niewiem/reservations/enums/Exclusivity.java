package com.infoshareacademy.niewiem.reservations.enums;

public enum Exclusivity {
    EXCLUSIVE(
            "exclusive",
            "((r.startTime > :start AND r.startTime < :end) AND " +
                    "(r.endTime > :start AND r.endTime < :end))"),
    INCLUSIVE(
            "inclusive",
            "((r.startTime > :start AND r.startTime < :end) OR " +
                    "(r.endTime > :start AND r.endTime < :end) OR " +
                    "(r.startTime < :start AND r.startTime < :end AND r.endTime > :start AND r.endTime > :end))");

    private String message;
    private String query;

    Exclusivity(String message, String query) {
        this.message = message;
        this.query = query;
    }

    public String getMessage() {
        return message;
    }

    public String getQuery() {
        return query;
    }
}
