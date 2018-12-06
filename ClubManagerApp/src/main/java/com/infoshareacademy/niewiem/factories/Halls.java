package com.infoshareacademy.niewiem.factories;

import com.infoshareacademy.niewiem.Hall;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Halls {

    private static final Logger LOG = LogManager.getLogger(Halls.class);

    public static Hall create(String name) {
        Integer newHallID = 1; // todo: check available ID, DataProvider.getNextAvailableHallID()
        Hall hall = new Hall(newHallID, name);
        // todo: save hall to halls.csv, DataProvider.saveHallInCsv()
        return hall;
    }

    public static Hall load(Integer hallID) {
        return null;
    }
}
