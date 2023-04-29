module SmartHome {

    interface Device {
        string showHelp();
    };

    interface Thermometer extends Device {
        void measureTemperature();
        double getTemperature();
    };

    interface RollerBlinds extends Device  {
        void raiseBlinds();
        void lowerBlinds();
        bool isRaised();
    };

    class Americano {
        int waterUsage = 11;
        int beansUsage = 3;
        int filtersUsage = 1;
        int milkUsage = 0;
        int groundsProduced = 2;
    };

    class Espresso {
        int waterUsage = 4;
        int beansUsage = 4;
        int filtersUsage = 0;
        int milkUsage = 0;
        int groundsProduced = 1;
    };

    class Latte {
        int waterUsage = 4;
        int beansUsage = 4;
        int filtersUsage = 0;
        int milkUsage = 9;
        int groundsProduced = 1;
    };

    interface CoffeeMachine extends Device  {
        bool prepareAmericano();
        double getRemainingBeansPercentage();
        double getRemainingWaterPercentage();
        double getGroundsContainerFillPercentage();
        void refillBeans();
        void refillWater();
        void emptyGroundsContainer();
    };

    interface FilterCoffeeMachine extends CoffeeMachine {
        double getRemainingFiltersPercentage();
        void refillFilters();
    };

    interface EspressoCoffeeMachine extends CoffeeMachine {
        bool prepareLatte();
        bool prepareEspresso();
        double getRemainingMilkPercentage();
        void refillMilk();
    };
}
