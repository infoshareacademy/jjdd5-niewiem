package com.infoshareacademy.niewiem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ConsoleReader {
    private Scanner sc;

    public ConsoleReader() {
        this.sc = new Scanner(System.in);
    }

    public int readInt(){
        // check if input is actually int
        int input = sc.nextInt();
        sc.nextLine();
        return input;
    }

    public LocalDate readDate(){
        String input = sc.nextLine();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(input, dateFormat);
    }
    public LocalTime readTime(){
        String input = sc.nextLine();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(input, timeFormat);
    }


    public String readString(){
        return sc.nextLine();
    }
}
