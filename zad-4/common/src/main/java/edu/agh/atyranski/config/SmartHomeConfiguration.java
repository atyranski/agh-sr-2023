package edu.agh.atyranski.config;

import SmartHome.EspressoCoffeeMachinePrx;
import SmartHome.FilterCoffeeMachinePrx;
import SmartHome.RollerBlindsPrx;
import SmartHome.ThermometerPrx;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Object;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;
import edu.agh.atyranski.servant.EspressoCoffeeMachineI;
import edu.agh.atyranski.servant.FilterCoffeeMachineI;
import edu.agh.atyranski.servant.RollerBlindsI;
import edu.agh.atyranski.servant.ThermometerI;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SmartHomeConfiguration {

    public final static int PORT = 10000;

    public static final List<DeviceConfiguration> DEFAULT_DEVICES = List.of(
            DeviceConfiguration.builder()
                    .id("1")
                    .name("ConferenceRoom-RollerBlinds")
                    .proxyType(RollerBlindsPrx.class)
                    .servantType(RollerBlindsI.class)
                    .build(),
            DeviceConfiguration.builder()
                    .id("2")
                    .name("Kitchen-EspressoCoffeeMachine")
                    .proxyType(EspressoCoffeeMachinePrx.class)
                    .servantType(EspressoCoffeeMachineI.class)
                    .build(),
            DeviceConfiguration.builder()
                    .id("3")
                    .name("BossRoom-FilterCoffeeMachine")
                    .proxyType(FilterCoffeeMachinePrx.class)
                    .servantType(FilterCoffeeMachineI.class)
                    .build(),
            DeviceConfiguration.builder()
                    .id("4")
                    .name("Corridor-Thermometer")
                    .proxyType(ThermometerPrx.class)
                    .servantType(ThermometerI.class)
                    .build()
    );

    public static RollerBlindsPrx createRollerBlindsProxy(Communicator communicator, String name, int port) {
        ObjectPrx objectProxy = communicator.stringToProxy(name + ":default -p " + port);

        return RollerBlindsPrx.checkedCast(objectProxy);
    }

    public static FilterCoffeeMachinePrx createFilterCoffeeMachineProxy(Communicator communicator, String name, int port) {
        ObjectPrx objectProxy = communicator.stringToProxy(name + ":default -p " + port);

        return FilterCoffeeMachinePrx.checkedCast(objectProxy);
    }

    public static EspressoCoffeeMachinePrx createEspressoCoffeeMachineProxy(Communicator communicator, String name, int port) {
        ObjectPrx objectProxy = communicator.stringToProxy(name + ":default -p " + port);

        return EspressoCoffeeMachinePrx.checkedCast(objectProxy);
    }

    public static ThermometerPrx createThermometerProxy(Communicator communicator, String name, int port) {
        ObjectPrx objectProxy = communicator.stringToProxy(name + ":default -p " + port);

        return ThermometerPrx.checkedCast(objectProxy);
    }

    public static Map<String, Device> createDevicesProxiesMap(
            Communicator communicator,
            List<DeviceConfiguration> configurations,
            int port) {

        final Map<String, Device> devicesMap = new HashMap<>();

        for (DeviceConfiguration configuration: configurations) {
            if (RollerBlindsPrx.class.equals(configuration.getProxyType())) {
                devicesMap.put(
                        configuration.getId(),
                        Device.builder()
                                .id(configuration.getId())
                                .name(configuration.getName())
                                .deviceProxy(createRollerBlindsProxy(communicator, configuration.getName(), port))
                                .build());
            } else if (FilterCoffeeMachinePrx.class.equals(configuration.getProxyType())) {
                devicesMap.put(
                        configuration.getId(),
                        Device.builder()
                                .id(configuration.getId())
                                .name(configuration.getName())
                                .deviceProxy(createFilterCoffeeMachineProxy(communicator, configuration.getName(), port))
                                .build());
            } else if (EspressoCoffeeMachinePrx.class.equals(configuration.getProxyType())) {
                devicesMap.put(
                        configuration.getId(),
                        Device.builder()
                                .id(configuration.getId())
                                .name(configuration.getName())
                                .deviceProxy(createEspressoCoffeeMachineProxy(communicator, configuration.getName(), port))
                                .build());
            } else if (ThermometerPrx.class.equals(configuration.getProxyType())) {
                devicesMap.put(
                        configuration.getId(),
                        Device.builder()
                                .id(configuration.getId())
                                .name(configuration.getName())
                                .deviceProxy(createThermometerProxy(communicator, configuration.getName(), port))
                                .build());
            } else {
                throw new RuntimeException("Unexpected proxy class provided as a device type");
            }
        }

        return devicesMap;
    }

    public static List<Object> createDevicesServants(
            List<DeviceConfiguration> deviceConfigurations,
            ObjectAdapter adapter) {

        final List<Object> servants = new LinkedList<>();

        for (DeviceConfiguration configuration: deviceConfigurations) {
            try {
                Object servant = configuration.getServantType().getDeclaredConstructor().newInstance();
                adapter.add(servant, Util.stringToIdentity(configuration.getName()));
                servants.add(servant);

            } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                     NoSuchMethodException e) {
                System.out.printf("Unable to create servant %s - unknown servant class\n", configuration.getName());
            }
        }

        return servants;
    }

}
