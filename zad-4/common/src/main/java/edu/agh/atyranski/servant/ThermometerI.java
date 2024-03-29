package edu.agh.atyranski.servant;

import SmartHome.Thermometer;
import com.zeroc.Ice.Current;

import static edu.agh.atyranski.utils.CustomLogger.printServerLog;

public class ThermometerI implements Thermometer {

    private static final long serialVersionUID = 1;

    private double temperature = 36;

    @Override
    public void measureTemperature(Current current) {
        int value = (int) ((Math.random() * (2800 - 1200)) + 1200);
        temperature = (double) value / 100;

        printServerLog(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), String.format("temperature has been measured: %f°C", temperature));
    }

    @Override
    public double getTemperature(Current current) {
        printServerLog(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "checked thermometer temperature");
        return temperature;
    }

    @Override
    public String showHelp(Current current) {
        return """
                [1] showHelp            -show possible actions"
                [2] measureTemperature  -check if blinds are raised
                [3] getTemperature      -raise blinds
                [4] exit                -return to device menu
                """;
    }
}
