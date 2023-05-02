package edu.agh.atyranski.servant;

import SmartHome.Americano;
import SmartHome.BrewResult;
import SmartHome.FilterCoffeeMachine;
import com.zeroc.Ice.Current;

import static edu.agh.atyranski.utils.CustomLogger.printServerError;
import static edu.agh.atyranski.utils.CustomLogger.printServerLog;

public class FilterCoffeeMachineI implements FilterCoffeeMachine {

    private static final long serialVersionUID = 1;

    private final int WATER_CAPACITY = 20;
    private final int BEANS_CAPACITY = 25;
    private final int FILTERS_CAPACITY = 1;
    private final int GROUNDS_CONTAINER_CAPACITY = 1;

    private final Americano americano = new Americano();

    private int waterAmount = WATER_CAPACITY;
    private int beansAmount = BEANS_CAPACITY;
    private int filtersAmount = FILTERS_CAPACITY;
    private int groundsAmount = 0;

    @Override
    public String showHelp(Current current) {
        return """
            [ 1] showHelp                            -show possible actions
            [ 2] prepareAmericano                    -call express to make an americano
            [ 3] getRemainingBeansPercentage         -get percentage of remaining coffee beans
            [ 4] getRemainingWaterPercentage         -get percentage of remaining water
            [ 5] getRemainingFiltersPercentage       -get percentage of remaining filters
            [ 6] getGroundsContainerFillPercentage   -get percentage of grounds container fill
            [ 7] refillBeans                         -call express to refill coffee beans
            [ 8] refillWater                         -call express to refill water
            [ 9] refillFilters                       -call express to refill filters
            [10] emptyGroundsContainer               -call express to remove grounds from container
            [11] exit                                -return to device menu
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
    public double getRemainingBeansPercentage(Current current) {
        printServerLog(this.getClass(),new Object() {}.getClass().getEnclosingMethod().getName(), "checked remaining coffee beans percentage");
        return (double) beansAmount / BEANS_CAPACITY;
    }

    @Override
    public double getRemainingWaterPercentage(Current current) {
        printServerLog(this.getClass(),new Object() {}.getClass().getEnclosingMethod().getName(), "checked remaining water percentage");
        return (double) waterAmount / WATER_CAPACITY;
    }

    @Override
    public double getRemainingFiltersPercentage(Current current) {
        printServerLog(this.getClass(),new Object() {}.getClass().getEnclosingMethod().getName(), "checked remaining filters percentage");
        return (double) filtersAmount / FILTERS_CAPACITY;
    }

    @Override
    public double getGroundsContainerFillPercentage(Current current) {
        printServerLog(this.getClass(),new Object() {}.getClass().getEnclosingMethod().getName(), "checked grounds container fill percentage");
        return (double) groundsAmount / GROUNDS_CONTAINER_CAPACITY;
    }

    @Override
    public void refillBeans(Current current) {
        printServerLog(this.getClass(),new Object() {}.getClass().getEnclosingMethod().getName(), "coffee beans has been refilled");
        beansAmount = BEANS_CAPACITY;
    }

    @Override
    public void refillWater(Current current) {
        printServerLog(this.getClass(),new Object() {}.getClass().getEnclosingMethod().getName(), "water has been refilled");
        waterAmount = WATER_CAPACITY;
    }

    @Override
    public void refillFilters(Current current) {
        printServerLog(this.getClass(),new Object() {}.getClass().getEnclosingMethod().getName(), "filters has been refilled");
        filtersAmount = FILTERS_CAPACITY;
    }

    @Override
    public void emptyGroundsContainer(Current current) {
        printServerLog(this.getClass(),new Object() {}.getClass().getEnclosingMethod().getName(), "grounds container has been emptied");
        groundsAmount = 0;
    }

}
