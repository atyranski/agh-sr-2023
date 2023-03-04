package edu.agh.atyranski.client;

import edu.agh.atyranski.client.exception.MissingClientNicknameException;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientConfig {

    final private InetAddress address;
    final private int port;
    final private String nickname;

    public ClientConfig(InetAddress address, int port, String nickname) {
        this.address = address;
        this.port = port;
        this.nickname = nickname;
    }

    public static ClientConfig getConfig(String[] arguments)
            throws UnknownHostException {

        if (arguments.length == 3) {
            return new ClientConfig(
                    InetAddress.getByName(arguments[0]),
                    Integer.parseInt(arguments[1]),
                    arguments[2]);
        }

//        if (arguments.length == 1) {
            return new ClientConfig(
                    InetAddress.getByName("127.0.0.1"),
                    3000,
                    "Test");
//                    arguments[0]);
//        }

//        throw new MissingClientNicknameException();
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
