package edu.agh.atyranski.client;

import SmartHome.EspressoCoffeeMachinePrx;
import SmartHome.FilterCoffeeMachinePrx;
import SmartHome.RollerBlindsPrx;
import SmartHome.ThermometerPrx;
import SmartHome._EspressoCoffeeMachinePrxI;
import SmartHome._FilterCoffeeMachinePrxI;
import SmartHome._RollerBlindsPrxI;
import SmartHome._ThermometerPrxI;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.LocalException;
import com.zeroc.Ice.Util;

import edu.agh.atyranski.config.Device;
import edu.agh.atyranski.config.SmartHomeConfiguration;

import java.util.Map;
import java.util.Scanner;

public class Client {

    private final String[] args;

    private Map<String, Device> devices;

    public Client(String[] args) {
        this.args = args;
    }

    private void start() {
        try (Communicator communicator = Util.initialize(args);
             Scanner scanner = new Scanner(System.in)) {

            this.devices = SmartHomeConfiguration.createDevicesProxiesMap(
                    communicator,
                    SmartHomeConfiguration.DEFAULT_DEVICES,
                    SmartHomeConfiguration.PORT);

            for (Map.Entry<String, Device> entry: devices.entrySet()) {
                if (entry.getValue().getDeviceProxy() == null) {
                    throw new Error("Invalid proxy");
                }
            }

            handleUserInput(scanner);
        }

        System.exit(0);
    }

    private void handleUserInput(Scanner scanner) {
        String userInput;
        boolean isRunning = true;

        do {
            try {
                System.out.print("> ");
                userInput = scanner.nextLine();
                String[] arguments = userInput.split(" ");

                switch (arguments[0]) {
                    case "help" -> printHelp();
                    case "list" -> listDevices();
                    case "use" -> useDevice(arguments[1], scanner);
                    case "exit" -> isRunning = false;
                }
            } catch (LocalException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        } while (isRunning);
    }

    private static void printHelp() {
        System.out.println("help        -get list of possible commands");
        System.out.println("list        -list all possible devices");
        System.out.println("use [name]  -use resource of provided name");
        System.out.println("exit        -stop client");
    }

    private void listDevices() {
        System.out.printf("| %10s | %25s | %40s |\n", "id", "type", "name");
        System.out.println("|-----------------------------------------------------------------------------------|");

        for (Map.Entry<String, Device> entry: devices.entrySet()) {
            String type = entry.getValue().getDeviceProxy().getClass().getSimpleName();
            System.out.printf("| %10s | %25s | %40s |\n",
                    entry.getValue().getId(),
                    type.substring(1, type.length() - 4),
                    entry.getValue().getName());
        }

        System.out.println("|-----------------------------------------------------------------------------------|");
    }

    private void useDevice(String id, Scanner scanner) {
        Device device = devices.get(id);

        if (_RollerBlindsPrxI.class.equals(device.getDeviceProxy().getClass())) {
            handleRollerBlinds(scanner, device.getName(), (RollerBlindsPrx) device.getDeviceProxy());
        } else if (_EspressoCoffeeMachinePrxI.class.equals(device.getDeviceProxy().getClass())) {
            handleEspressoCoffeeMachine(scanner, device.getName(), (EspressoCoffeeMachinePrx) device.getDeviceProxy());
        } else if (_FilterCoffeeMachinePrxI.class.equals(device.getDeviceProxy().getClass())) {
            handleFilterCoffeeMachine(scanner, device.getName(), (FilterCoffeeMachinePrx) device.getDeviceProxy());
        } else if (_ThermometerPrxI.class.equals(device.getDeviceProxy().getClass())) {
            handleThermometer(scanner, device.getName(), (ThermometerPrx) device.getDeviceProxy());
        } else {
            throw new RuntimeException("Unknown type of proxy");
        }
    }

    private void handleRollerBlinds(Scanner scanner, String name, RollerBlindsPrx proxy) {
        String userInput;
        boolean isActive = true;

        do {
            System.out.printf("[%s] > ", name);
            userInput = scanner.nextLine();

            switch (userInput) {
                case "showHelp" -> System.out.println(proxy.showHelp());
                case "isRaised" -> System.out.println(proxy.isRaised());
                case "raiseBlinds" -> proxy.raiseBlinds();
                case "lowerBlinds" -> proxy.lowerBlinds();
                case "exit" -> isActive = false;
                default -> System.out.println("[Error] Unknown command. Use 'showHelp' to see possible commands");
            }

        } while (isActive);
    }

    private void handleEspressoCoffeeMachine(Scanner scanner, String name, EspressoCoffeeMachinePrx proxy) {
        String userInput;
        boolean isActive = true;

        do {
            System.out.printf("[%s] > ", name);
            userInput = scanner.nextLine();

            switch (userInput) {
                case "showHelp" -> System.out.println(proxy.showHelp());
                case "prepareAmericano" -> proxy.prepareAmericano();
                case "prepareEspresso" -> proxy.prepareEspresso();
                case "prepareLatte" -> proxy.prepareLatte();
                case "getRemainingBeansPercentage" -> System.out.println(proxy.getRemainingBeansPercentage());
                case "getRemainingWaterPercentage" -> System.out.println(proxy.getRemainingWaterPercentage());
                case "getRemainingMilkPercentage" -> System.out.println(proxy.getRemainingMilkPercentage());
                case "getGroundsContainerFillPercentage" -> System.out.println(proxy.getGroundsContainerFillPercentage());
                case "refillBeans" -> proxy.refillBeans();
                case "refillWater" -> proxy.refillWater();
                case "refillMilk" -> proxy.refillMilk();
                case "emptyGroundsContainer" -> proxy.emptyGroundsContainer();
                case "exit" -> isActive = false;
                default -> System.out.println("[Error] Unknown command. Use 'showHelp' to see possible commands");
            }

        } while (isActive);
    }

    private void handleFilterCoffeeMachine(Scanner scanner, String name, FilterCoffeeMachinePrx proxy) {
        String userInput;
        boolean isActive = true;

        do {
            System.out.printf("[%s] > ", name);
            userInput = scanner.nextLine();

            switch (userInput) {
                case "showHelp" -> System.out.println(proxy.showHelp());
                case "prepareAmericano" -> proxy.prepareAmericano();
                case "getRemainingBeansPercentage" -> System.out.println(proxy.getRemainingBeansPercentage());
                case "getRemainingWaterPercentage" -> System.out.println(proxy.getRemainingWaterPercentage());
                case "getRemainingFiltersPercentage" -> System.out.println(proxy.getRemainingFiltersPercentage());
                case "getGroundsContainerFillPercentage" -> System.out.println(proxy.getGroundsContainerFillPercentage());
                case "refillBeans" -> proxy.refillBeans();
                case "refillWater" -> proxy.refillWater();
                case "refillFilters" -> proxy.refillFilters();
                case "emptyGroundsContainer" -> proxy.emptyGroundsContainer();
                case "exit" -> isActive = false;
                default -> System.out.println("[Error] Unknown command. Use 'showHelp' to see possible commands");
            }

        } while (isActive);
    }

    private void handleThermometer(Scanner scanner, String name, ThermometerPrx proxy) {
        String userInput;
        boolean isActive = true;

        do {
            System.out.printf("[%s] > ", name);
            userInput = scanner.nextLine();

            switch (userInput) {
                case "showHelp" -> System.out.println(proxy.showHelp());
                case "measureTemperature" -> proxy.measureTemperature();
                case "getTemperature" -> System.out.println(proxy.getTemperature());
                case "exit" -> isActive = false;
                default -> System.out.println("[Error] Unknown command. Use 'showHelp' to see possible commands");
            }

        } while (isActive);
    }


    public static void main(String[] args) {
        final Client client = new Client(args);
        client.start();
    }
}
