package com.infoshareacademy.niewiem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleReader {
    private Scanner sc;

    public ConsoleReader() {
        this.sc = new Scanner(System.in);
    }

    public int readInt() {

        String input = sc.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number: ");
            //todo: log 'e' exception
            return readInt();
        }
    }

    public LocalDate readDate() {
        String input = sc.nextLine().trim();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(input, dateFormat);
        } catch (DateTimeParseException e) {
            //todo::log 'e' exception
            System.out.println("Please enter the date in the given format (yyyy-MM-dd)");
            return readDate();
        }
    }

    public LocalTime readTime() {
        String input = sc.nextLine().trim();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        try {
            return LocalTime.parse(input, timeFormat);
        } catch (DateTimeParseException e) {
            System.out.println(e);
            //todo::log 'e' exception
            System.out.println("Please enter the time in the given format (HH:mm)");
            return readTime();
        }
    }


    public String readString() {
        return sc.nextLine().trim();
    }
}
