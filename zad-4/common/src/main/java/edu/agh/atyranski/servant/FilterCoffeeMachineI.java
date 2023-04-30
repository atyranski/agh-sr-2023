package edu.agh.atyranski.servant;

import SmartHome.Americano;
import SmartHome.FilterCoffeeMachine;
import com.zeroc.Ice.Current;

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
    public boolean prepareAmericano(Current current) {
        if (waterAmount < americano.waterUsage ||
                beansAmount < americano.beansUsage ||
                filtersAmount < americano.filtersUsage ||
                groundsAmount > GROUNDS_CONTAINER_CAPACITY) {
            return false;
        }

        waterAmount -= americano.waterUsage;
        beansAmount -= americano.beansUsage;
        filtersAmount -= americano.filtersUsage;
        groundsAmount += americano.groundsProduced;

        return true;
    }

    @Override
    public double getRemainingBeansPercentage(Current current) {
        return (double) beansAmount / BEANS_CAPACITY;
    }

    @Override
    public double getRemainingWaterPercentage(Current current) {
        return (double) waterAmount / WATER_CAPACITY;
    }

    @Override
    public double getGroundsContainerFillPercentage(Current current) {
        return (double) groundsAmount / GROUNDS_CONTAINER_CAPACITY;
    }

    @Override
    public double getRemainingFiltersPercentage(Current current) {
        return (double) filtersAmount / FILTERS_CAPACITY;
    }

    @Override
    public void refillBeans(Current current) {
        beansAmount = BEANS_CAPACITY;
    }

    @Override
    public void refillWater(Current current) {
        waterAmount = WATER_CAPACITY;
    }

    @Override
    public void emptyGroundsContainer(Current current) {
        groundsAmount = 0;
    }

    @Override
    public void refillFilters(Current current) {
        filtersAmount = FILTERS_CAPACITY;
    }

}
