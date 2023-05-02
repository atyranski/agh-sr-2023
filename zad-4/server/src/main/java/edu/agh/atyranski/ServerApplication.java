package edu.agh.atyranski;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Object;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import edu.agh.atyranski.config.SmartHomeConfiguration;

import java.util.List;

public class ServerApplication {

    private final String[] args;

    private ServerApplication(String[] args) {
        this.args = args;
    }

    private void start() {
        try (Communicator communicator = Util.initialize(args)) {
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints(
                    "SmartHomeAdapter",
                    "default -p " + SmartHomeConfiguration.PORT);

            List<Object> servants = SmartHomeConfiguration.createDevicesServants(SmartHomeConfiguration.DEFAULT_DEVICES, adapter);

            adapter.activate();

            System.out.println("Server started");
            communicator.waitForShutdown();
        }
    }

    public static void main(String[] args) {
        ServerApplication server = new ServerApplication(args);
        server.start();
    }
}
