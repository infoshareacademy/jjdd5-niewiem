package com.infoshareacademy.niewiem.reservations.enums;

public enum Period {
    ACTIVE("Active"),
    ALL("All"),


    TODAY("Today"),
    YESTERDAY("Yesterday"),
    TOMORROW("Tomorrow"),

    LAST24H("Last 24 hours"),

    THIS_MONTH("This month"),

    HISTORY("History"),
    UPCOMING("Upcoming"),

    CUSTOM("Custom period");

    private String text;

    Period(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }
}