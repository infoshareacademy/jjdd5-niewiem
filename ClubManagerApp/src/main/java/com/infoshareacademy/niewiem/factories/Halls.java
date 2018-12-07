package com.infoshareacademy.niewiem.factories;

import com.infoshareacademy.niewiem.DataProvider;
import com.infoshareacademy.niewiem.Hall;

import java.util.OptionalInt;

public class Halls {
    public static Hall create(String name){
        Integer newHallID = getNextAvailableHallId();
        Hall hall = new Hall(newHallID, name);
        DataProvider.saveHallInCsv(hall);
        return hall;
    }

    public static Integer getNextAvailableHallId() {
        OptionalInt nextAvailableId = DataProvider.loadHallsAsList().stream()
                .mapToInt(e -> e.getHallId())
                .max();

        if (nextAvailableId.isPresent()) {
            return nextAvailableId.getAsInt() + 1;
        }
        return 1;
    }

    public static Hall load(Integer hallID, String hallName){
        return new Hall(hallID, hallName);
    }
}
