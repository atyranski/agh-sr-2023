package edu.agh.atyranski.server;

import java.net.InetAddress;

public class ServerConfig {
    final private InetAddress address;
    final private int port;
    final private int backlog;

    public ServerConfig(InetAddress address, int port, int backlog) {
        this.address = address;
        this.port = port;
        this.backlog = backlog;
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
