package com.infoshareacademy.niewiem.factories;

import com.infoshareacademy.niewiem.Hall;

public class Halls {
    public static Hall create(String name){
        Integer newHallID = 1; // todo: check available ID, DataProvider.getNextAvailableHallID()
        Hall hall = new Hall(newHallID, name);
        // todo: save hall to halls.csv, DataProvider.saveHallInCsv()
        return hall;
    }

    public static Hall load(Integer hallID){
        return null;
    }
}
