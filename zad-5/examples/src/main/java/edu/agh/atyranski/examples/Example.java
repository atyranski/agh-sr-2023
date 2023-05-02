package edu.agh.atyranski.examples;

public class Example {
    public static String helloWorld() {
        return "Hello world!";
    }

    public static int add(AdditionAddends addends) {
        return addends.a + addends.b;
    }

    public static class AdditionAddends {
        int a;
        int b;
    }
}