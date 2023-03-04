package edu.agh.atyranski.client;

import edu.agh.atyranski.client.exception.MissingClientNicknameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientApplication {

    private final static Logger log = LoggerFactory.getLogger(ClientApplication.class.getSimpleName());

    public static void main(String[] args) {
        try {
            ClientConfig config = ClientConfig.getConfig(args);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Nickname: ");
            String nickname = scanner.nextLine();

            ClientService service = new ClientService(config, nickname);
            service.start();
            service.close();

        } catch (UnknownHostException e) {
            log.warn("unknown hosts", e);
        }
    }
}
