package edu.agh.atyranski.server;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import com.zeroc.Ice.Object;
import edu.agh.atyranski.config.SmartHomeConfiguration;

import java.util.List;

public class Server {

    private final String[] args;

    private Server(String[] args) {
        this.args = args;
    }

    private void start() {
        try (Communicator communicator = Util.initialize(args)) {
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints(
                    "SmartHomeAdapter",
                    "default -p " + SmartHomeConfiguration.PORT);

            List<Object> servants = SmartHomeConfiguration.createDevicesServants(SmartHomeConfiguration.DEFAULT_DEVICES, adapter);

            adapter.activate();
            communicator.waitForShutdown();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(args);
        server.start();
    }
}
