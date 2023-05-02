package edu.agh.atyranski;

import SmartHome.BrewResult;
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

public class ClientApplication {

    private final String[] args;

    private Map<String, Device> devices;
    private UserInputHandler userInputHandler;

    public ClientApplication(String[] args) {
        this.args = args;
    }

    private void start() {
        try (Communicator communicator = Util.initialize(args);
             Scanner scanner = new Scanner(System.in)) {

            this.devices = SmartHomeConfiguration.createDevicesProxiesMap(
                    communicator,
                    SmartHomeConfiguration.DEFAULT_DEVICES,
                    SmartHomeConfiguration.PORT);

            this.userInputHandler = new UserInputHandler(devices);

            for (Map.Entry<String, Device> entry: devices.entrySet()) {
                if (entry.getValue().getDeviceProxy() == null) {
                    throw new Error("Invalid proxy");
                }
            }

            userInputHandler.handleUserInput(scanner);
        }

        System.exit(0);
    }

    public static void main(String[] args) {
        final ClientApplication client = new ClientApplication(args);
        client.start();
    }
}
