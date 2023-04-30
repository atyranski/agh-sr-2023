package edu.agh.atyranski.servant;

import SmartHome.RollerBlinds;
import com.zeroc.Ice.Current;

import java.util.Scanner;

public class RollerBlindsI implements RollerBlinds {

    private static final long serialVersionUID = 1;

    private boolean isRaised = true;

    @Override
    public void raiseBlinds(Current current) {
        isRaised = true;
    }

    @Override
    public void lowerBlinds(Current current) {
        isRaised = false;
    }

    @Override
    public boolean isRaised(Current current) {
        return isRaised;
    }

    @Override
    public String showHelp(Current current) {
        return """
                [1] showHelp        -show possible actions"
                [2] isRaised        -check if blinds are raised
                [3] raiseBlinds     -raise blinds
                [4] lowerBlinds     -lower blinds
                [5] exit            -return to device menu
                """;
    }
}
