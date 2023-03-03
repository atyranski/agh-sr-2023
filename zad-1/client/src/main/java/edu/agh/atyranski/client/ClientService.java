package edu.agh.atyranski.client;

import edu.agh.atyranski.util.Action;
import edu.agh.atyranski.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientService {
    private final static Logger log = LoggerFactory.getLogger(ClientService.class.getSimpleName());

    private final ClientConfig config;

    private boolean running = true;

    public ClientService(ClientConfig config) {
        this.config = config;
    }

    private void registerNickname(DataInputStream input, DataOutputStream output)
            throws IOException {
        log.debug("sending request to register nickname");

        output.writeUTF(Action.LOG_IN.label);
        output.writeUTF(config.getNickname());

        Response response = Response.of(input.readUTF());

        log.debug("successfully logged into server with nickname: {}", config.getNickname());
    }

    public void start() {
        try (Socket socket = new Socket(config.getAddress(), config.getPort());
             Scanner scanner = new Scanner(System.in);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            log.debug("connected to server: {}:{}\n", socket.getInetAddress(), socket.getPort());
            registerNickname(input, output);

            while (running) {
                System.out.print("> ");
                String message = scanner.nextLine();
                running = false;
            }
        } catch (IOException e) {
            log.warn("occurred problem while creating the socket", e);
        }
    }

}
