package com.infoshareacademy.niewiem.reservations.enums;

public enum Period {
    CUSTOM("Custom period"),

    YESTERDAY("Yesterday"),
    TODAY("Today"),
    TOMORROW("Tomorrow"),

    LAST24H("Last 24 hours"),

    THIS_MONTH("This month"),

    HISTORY("History"),
    ACTIVE("Active"),
    UPCOMING("Upcoming");

    private String text;

    Period(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }
}