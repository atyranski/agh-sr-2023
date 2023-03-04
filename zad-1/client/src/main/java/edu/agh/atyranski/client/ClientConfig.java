package edu.agh.atyranski.client;

import edu.agh.atyranski.client.exception.MissingClientNicknameException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class ClientConfig {

    final private InetAddress address;
    final private int port;
    final private String nickname;

    private ClientConfig(Properties properties)
            throws UnknownHostException {

        if (properties.get("nickname") == null) {
            throw new MissingClientNicknameException();
        } else {
            this.nickname = (String) properties.get("nickname");
        }

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
    }

    public static ClientConfig getConfig(String[] arguments)
            throws UnknownHostException {

        Properties properties = parseArguments(arguments);
        return new ClientConfig(properties);
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

    public String getNickname() {
        return nickname;
    }


}
