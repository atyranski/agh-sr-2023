package edu.agh.atyranski.client;

import edu.agh.atyranski.client.exception.MissingClientNicknameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientApplication {

    private final static Logger log = LoggerFactory.getLogger(ClientApplication.class.getSimpleName());

    private static ClientConfig getConfig(String[] arguments)
            throws UnknownHostException {

        if (arguments.length == 3) {
            return new ClientConfig(
                    InetAddress.getByName(arguments[0]),
                    Integer.parseInt(arguments[1]),
                    arguments[2]);
        }

        if (arguments.length == 1) {
            return new ClientConfig(
                    InetAddress.getByName("127.0.0.1"),
                    3000,
                    arguments[0]);
        }

        throw new MissingClientNicknameException();
    }

    public static void main(String[] args) {
        try {
            ClientConfig config = getConfig(args);
            ClientService service = new ClientService(config);
            service.start();

        } catch (UnknownHostException e) {
            log.warn("unknown hosts", e);
        }
    }
}
