package edu.agh.atyranski.server;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerConfig {
    final private InetAddress address;
    final private int port;
    final private int backlog;

    public ServerConfig(InetAddress address, int port, int backlog) {
        this.address = address;
        this.port = port;
        this.backlog = backlog;
    }

    public static ServerConfig getConfig(String[] arguments)
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

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getBacklog() {
        return backlog;
    }
}
