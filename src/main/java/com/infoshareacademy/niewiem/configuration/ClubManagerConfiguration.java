package com.infoshareacademy.niewiem.configuration;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ClubManagerConfiguration {

    private static final String TIME_FORMAT = "timeFormat";
    private static final String TIME_FORMAT_DEFAULT = "mm";

    private Properties properties;

    public ClubManagerConfiguration(String file) {
        this.properties = new Properties();
        loadProperties(file);
    }

    private void loadProperties(String file) {
        try {
            this.properties.load(new FileReader("app.properties"));
        } catch (IOException e) {
            System.out.println("Missing properties file.");
        }
    }

    public String getTimeFormat() {
        String timeFormat = properties.getProperty(TIME_FORMAT, TIME_FORMAT_DEFAULT);
        return timeFormat;
    }
}
