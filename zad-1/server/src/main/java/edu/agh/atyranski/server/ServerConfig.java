package edu.agh.atyranski.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class ServerConfig {
    final private InetAddress address;
    final private int port;
    final private int backlog;

    private ServerConfig(Properties properties)
            throws UnknownHostException {

        if (properties.get("address") == null) {
            this.address = InetAddress.getByName("127.0.0.1");
        } else {
            this.address = InetAddress.getByName((String) properties.get("address"));
        }

        if (properties.get("port") == null) {
            this.port = 3000;
        } else {
            this.port = Integer.parseInt((String) properties.get("port"));
        }

        if (properties.get("backlog") == null) {
            this.backlog = 100;
        } else {
            this.backlog = Integer.parseInt((String) properties.get("backlog"));
        }
    }

    public static ServerConfig getConfig(String[] arguments)
            throws UnknownHostException {

        Properties properties = parseArguments(arguments);
        return new ServerConfig(properties);
    }

    private static Properties parseArguments(String[] arguments) {
        Properties properties = new Properties();

        for (int i = 0; i < arguments.length; i = i + 2) {
            String key = arguments[i].substring(2);
            String value = arguments[i+1];
            properties.put(key, value);
        }

        return properties;
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
