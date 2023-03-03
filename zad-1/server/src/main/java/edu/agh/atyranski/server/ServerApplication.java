package edu.agh.atyranski.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerApplication {

    private final static Logger log = LoggerFactory.getLogger(ServerApplication.class.getSimpleName());

    private static ServerConfig getConfig(String[] arguments)
            throws UnknownHostException {

        if (arguments.length == 3) {
            return new ServerConfig(
                    InetAddress.getByName(arguments[0]),
                    Integer.parseInt(arguments[1]),
                    Integer.parseInt(arguments[2]));
        }

        return new ServerConfig(
                InetAddress.getByName("127.0.0.1"),
                3000,
                5);
    }

    public static void main(String[] args) {
        try {
            ServerConfig config = getConfig(args);
            ServerService service = new ServerService(config);
            service.start();
            service.close();

        } catch (UnknownHostException e) {
            log.warn("unknown hosts", e);
        }
    }
}
