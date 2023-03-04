package edu.agh.atyranski.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.UnknownHostException;

public class ServerApplication {

    private final static Logger log = LoggerFactory.getLogger(ServerApplication.class.getSimpleName());

    public static void main(String[] args) {
        try {
            ServerConfig config = ServerConfig.getConfig(args);
            ServerService service = new ServerService(config);
            service.start();
            service.close();

        } catch (UnknownHostException e) {
            log.warn("unknown hosts", e);
        } catch (IOException e) {
            log.warn("occurred problem while opening server socket", e);
        }
    }
}
