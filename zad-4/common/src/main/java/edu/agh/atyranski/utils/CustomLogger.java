package edu.agh.atyranski.utils;

public class CustomLogger {

    public static void printServerLog(Class<?> clazz, String methodName, String message) {
        System.out.printf("[%s > %s]: %s\n", clazz.getSimpleName(), methodName, message);
    }

    public static void printServerError(Class<?> clazz, String methodName, String message) {
        System.out.printf("[ERROR: %s > %s]: %s\n", clazz.getSimpleName(), methodName, message);
    }
}
