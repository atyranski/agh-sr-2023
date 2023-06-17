package edu.agh.atyranski.rabbitmq.space.util;

import java.util.Properties;

public class ConfigParser {

    public static Properties parseArguments(String[] arguments) {
        Properties properties = new Properties();

        for (int i = 0; i < arguments.length; i = i + 2) {
            String key = arguments[i].substring(2);
            String value = arguments[i+1];
            properties.put(key, value);
        }

        return properties;
    }
}
