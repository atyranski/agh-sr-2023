package edu.agh.atyranski.servant;

import SmartHome.Americano;
import SmartHome.BrewResult;
import SmartHome.Espresso;
import SmartHome.EspressoCoffeeMachine;
import SmartHome.Latte;
import com.zeroc.Ice.Current;

import static edu.agh.atyranski.utils.CustomLogger.printServerError;
import static edu.agh.atyranski.utils.CustomLogger.printServerLog;

public class EspressoCoffeeMachineI implements EspressoCoffeeMachine {

    private static final long serialVersionUID = 1;

    private final int WATER_CAPACITY = 25;
    private final int BEANS_CAPACITY = 30;
    private final int MILK_CAPACITY = 15;
    private final int GROUNDS_CONTAINER_CAPACITY = 10;

    private final Americano americano = new Americano();
    private final Espresso espresso = new Espresso();
    private final Latte latte = new Latte();

    private int waterAmount = WATER_CAPACITY;
    private int beansAmount = BEANS_CAPACITY;
    private int milkAmount = MILK_CAPACITY;
    private int groundsAmount = 0;

    @Override
    public String showHelp(Current current) {
        return """
            [ 1] showHelp                            -show possible actions
            [ 2] prepareAmericano                    -call express to make an americano
            [ 3] prepareEspresso                     -call express to make an espresso
            [ 4] prepareLatte                        -call express to make a latte
            [ 5] getRemainingBeansPercentage         -get percentage of remaining coffee beans
            [ 6] getRemainingWaterPercentage         -get percentage of remaining water
            [ 7] getRemainingMilkPercentage          -get percentage of remaining milk
            [ 8] getGroundsContainerFillPercentage   -get percentage of grounds container fill
            [ 9] refillBeans                         -call express to refill coffee beans
            [10] refillWater                         -call express to refill water
            [11] refillMilk                          -call express to refill milk
            [12] emptyGroundsContainer               -call express to remove grounds from container
            [13] exit                                -return to device menu
            """;
    }

    @Override
    public BrewResult prepareAmericano(Current current) {
        printServerLog(this.getClass(),new Object() {}.getClass().getEnclosingMethod().getName(), "americano requested");

        if (waterAmount < americano.waterUsage) {
            printServerError(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "not enough water");
            return BrewResult.NoWater;
        }

        if (beansAmount < americano.beansUsage) {
            printServerError(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "not enough beans");
            return BrewResult.NoBeans;
        }

        if (groundsAmount > GROUNDS_CONTAINER_CAPACITY) {
            printServerError(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "grounds container is full");
            return BrewResult.GroundsFull;
        }

        waterAmount -= americano.waterUsage;
        beansAmount -= americano.beansUsage;
        groundsAmount += americano.groundsProduced;

        printServerLog(this.getClass(),new Object() {}.getClass().getEnclosingMethod().getName(), "americano prepared");
        return BrewResult.Ready;
    }

    @Override
    public BrewResult prepareEspresso(Current current) {
        printServerLog(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "espresso requested");

        if (waterAmount < espresso.waterUsage) {
            printServerError(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "not enough water");
            return BrewResult.NoWater;
        }

        if (beansAmount < espresso.beansUsage) {
            printServerError(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "not enough beans");
            return BrewResult.NoBeans;
        }

        if (groundsAmount > GROUNDS_CONTAINER_CAPACITY) {
            printServerError(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "grounds container is full");
            return BrewResult.GroundsFull;
        }

        waterAmount -= espresso.waterUsage;
        beansAmount -= espresso.beansUsage;
        groundsAmount += espresso.groundsProduced;

        printServerLog(this.getClass(),new Object() {}.getClass().getEnclosingMethod().getName(), "espresso prepared");

        return BrewResult.Ready;
    }

    @Override
    public BrewResult prepareLatte(Current current) {
        printServerLog(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "latte requested");

        if (waterAmount < latte.waterUsage) {
            printServerError(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "not enough water");
            return BrewResult.NoWater;
        }

        if (beansAmount < latte.beansUsage) {
            printServerError(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "not enough beans");
            return BrewResult.NoBeans;
        }

        if (milkAmount < latte.milkUsage) {
            printServerError(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "not enough milk");
            return BrewResult.NoMilk;
        }

        if (groundsAmount > GROUNDS_CONTAINER_CAPACITY) {
            printServerError(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "grounds container is full");
            return BrewResult.GroundsFull;
        }

        waterAmount -= latte.waterUsage;
        beansAmount -= latte.beansUsage;
        milkAmount -= latte.milkUsage;
        groundsAmount += latte.groundsProduced;

        printServerLog(this.getClass(),new Object() {}.getClass().getEnclosingMethod().getName(), "latte prepared");

        return BrewResult.Ready;
    }

    @Override
    public double getRemainingBeansPercentage(Current current) {
        printServerLog(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), " checked remaining coffee beans percentage");
        return (double) beansAmount / BEANS_CAPACITY;
    }

    @Override
    public double getRemainingWaterPercentage(Current current) {
        printServerLog(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "checked remaining water percentage");
        return (double) waterAmount / WATER_CAPACITY;
    }

    @Override
    public double getRemainingMilkPercentage(Current current) {
        printServerLog(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "checked remaining milk percentage");
        return (double) milkAmount / MILK_CAPACITY;
    }


    @Override
    public double getGroundsContainerFillPercentage(Current current) {
        printServerLog(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "checked grounds container fill percentage");
        return (double) groundsAmount / GROUNDS_CONTAINER_CAPACITY;
    }

    @Override
    public void refillBeans(Current current) {
        printServerLog(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "coffee beans has been refilled");
        beansAmount = BEANS_CAPACITY;
    }

    @Override
    public void refillWater(Current current) {
        printServerLog(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "water has been refilled");
        waterAmount = WATER_CAPACITY;
    }


    @Override
    public void refillMilk(Current current) {
        printServerLog(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "filters has been refilled");
        milkAmount = MILK_CAPACITY;
    }

    @Override
    public void emptyGroundsContainer(Current current) {
        printServerLog(this.getClass(), new Object() {}.getClass().getEnclosingMethod().getName(), "grounds container has been emptied");
        groundsAmount = 0;
    }
}
