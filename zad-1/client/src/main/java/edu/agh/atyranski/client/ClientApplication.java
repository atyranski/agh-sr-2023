package edu.agh.atyranski.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.UnknownHostException;

public class ClientApplication {

    private final static Logger log = LoggerFactory.getLogger(ClientApplication.class.getSimpleName());

    public static void main(String[] args) {
        try {
            ClientConfig config = ClientConfig.getConfig(args);
            ClientService service = new ClientService(config);
            service.start();
            service.close();

        } catch (UnknownHostException e) {
            log.warn("unknown hosts", e);
        }
    }
}
