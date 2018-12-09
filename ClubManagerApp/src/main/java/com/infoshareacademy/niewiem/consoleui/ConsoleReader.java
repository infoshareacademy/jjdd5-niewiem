package com.infoshareacademy.niewiem.consoleui;

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

    public int enterInt() {
        String input = sc.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number: ");
            //todo: log 'e' exception
            return enterInt();
        }
    }

    public LocalDate enterDate() {
        String input = sc.nextLine().trim();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(input, dateFormat);
        } catch (DateTimeParseException e) {
            //todo::log 'e' exception
            System.out.println("Please enter the date in the given format (yyyy-MM-dd)");
            return enterDate();
        }
    }

    public LocalTime enterTime() {
        String input = sc.nextLine();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        try {
            return LocalTime.parse(input, timeFormat);
        } catch (DateTimeParseException e) {
            System.out.println(e);
            //todo::log 'e' exception
            System.out.println("Please enter the time in the given format (HH:mm)");
            return enterTime();
        }
    }

    public String enterString() {
        return sc.nextLine().trim();
    }
}
