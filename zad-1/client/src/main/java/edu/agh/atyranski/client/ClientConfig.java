package edu.agh.atyranski.client;

import java.net.InetAddress;

public class ClientConfig {

    final private InetAddress address;
    final private int port;
    final private String nickname;

    public ClientConfig(InetAddress address, int port, String nickname) {
        this.address = address;
        this.port = port;
        this.nickname = nickname;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String getNickname() {
        return nickname;
    }
}
