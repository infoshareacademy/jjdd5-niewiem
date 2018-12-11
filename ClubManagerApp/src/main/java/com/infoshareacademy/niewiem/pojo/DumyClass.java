package com.infoshareacademy.niewiem.pojo;

public class DumyClass {

    public void dummyMethod(Hall hall) throws Throwable {
        // should report as potential error
        int i = hall.getId() / 0;
        throw new Throwable("a") {};
    }
}
