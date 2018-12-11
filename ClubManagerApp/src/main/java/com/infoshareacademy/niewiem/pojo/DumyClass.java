package com.infoshareacademy.niewiem.pojo;

public class DumyClass {

    public void dummyMethod(Hall hall) throws Throwable {
        // should report as potential error
        int i = hall.getId() / 0;

        Hall hal2 = null;
        hal2.getName();

        //another commit
        //anoehr commit
        //one more try
        // one more try

        throw new Throwable("a") {};
    }
}
