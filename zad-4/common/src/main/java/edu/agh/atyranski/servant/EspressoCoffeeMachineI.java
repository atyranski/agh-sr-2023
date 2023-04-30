package edu.agh.atyranski.servant;

import SmartHome.Americano;
import SmartHome.Espresso;
import SmartHome.EspressoCoffeeMachine;
import SmartHome.Latte;
import com.zeroc.Ice.Current;

public class EspressoCoffeeMachineI implements EspressoCoffeeMachine{

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
    public boolean prepareAmericano(Current current) {
        if (waterAmount < americano.waterUsage ||
                beansAmount < americano.beansUsage ||
                groundsAmount > GROUNDS_CONTAINER_CAPACITY) {
            return false;
        }

        waterAmount -= americano.waterUsage;
        beansAmount -= americano.beansUsage;
        groundsAmount += americano.groundsProduced;

        return true;
    }

    @Override
    public boolean prepareEspresso(Current current) {
        if (waterAmount < espresso.waterUsage ||
                beansAmount < espresso.beansUsage ||
                groundsAmount > GROUNDS_CONTAINER_CAPACITY) {
            return false;
        }

        waterAmount -= espresso.waterUsage;
        beansAmount -= espresso.beansUsage;
        groundsAmount += espresso.groundsProduced;

        return true;
    }

    @Override
    public boolean prepareLatte(Current current) {
        if (waterAmount < latte.waterUsage ||
                beansAmount < latte.beansUsage ||
                milkAmount < latte.milkUsage ||
                groundsAmount > GROUNDS_CONTAINER_CAPACITY) {
            return false;
        }

        waterAmount -= latte.waterUsage;
        beansAmount -= latte.beansUsage;
        milkAmount -= latte.milkUsage;
        groundsAmount += latte.groundsProduced;

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
    public double getRemainingMilkPercentage(Current current) {
        return (double) milkAmount / MILK_CAPACITY;
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
    public void refillMilk(Current current) {
        milkAmount = MILK_CAPACITY;
    }

    @Override
    public void emptyGroundsContainer(Current current) {
        groundsAmount = 0;
    }
}
