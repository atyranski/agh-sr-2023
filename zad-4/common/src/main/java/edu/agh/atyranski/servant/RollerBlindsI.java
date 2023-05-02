package edu.agh.atyranski.servant;

import SmartHome.RollerBlinds;
import com.zeroc.Ice.Current;

import static edu.agh.atyranski.utils.CustomLogger.printServerLog;

public class RollerBlindsI implements RollerBlinds {

    private static final long serialVersionUID = 1;

    private boolean isRaised = true;

    @Override
    public void raiseBlinds(Current current) {
        printServerLog(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "blinds has been raised");
        isRaised = true;
    }

    @Override
    public void lowerBlinds(Current current) {
        printServerLog(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "blinds has been lowered");
        isRaised = false;
    }

    @Override
    public boolean isRaised(Current current) {
        printServerLog(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "checked if blinds are raised");
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
