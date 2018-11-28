package com.infoshareacademy.niewiem.configuration;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ClubManagerConfiguration {

    private static final String configPath = "app.properties";
    private static final String TIME_FORMAT_KEY = "timeFormat";
    private static final String TIME_FORMAT_DEFAULT = "yy-MM-dd_HH:mm:ss";

    private Properties properties;

    public ClubManagerConfiguration(String file) {
        this.properties = new Properties();
        loadProperties(file);
    }

    private void loadProperties(String file) {
        try {
            this.properties.load(new FileReader(configPath));
        } catch (IOException e) {
            System.out.println("Cannot read properties file");
        }
    }

    public String getTimeFormat() {
        return properties.getProperty(TIME_FORMAT_KEY, TIME_FORMAT_DEFAULT);
    }
}
