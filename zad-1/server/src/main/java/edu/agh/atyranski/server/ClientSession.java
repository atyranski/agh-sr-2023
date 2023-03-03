package edu.agh.atyranski.server;

import edu.agh.atyranski.util.Action;
import edu.agh.atyranski.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSession extends Thread {

    private final static Logger log = LoggerFactory.getLogger(ClientSession.class.getSimpleName());

    private final ServerService serverService;
    private final Socket socket;
    private boolean running = true;

    public ClientSession(ServerService serverService, Socket socket) {
        this.serverService = serverService;
        this.socket = socket;
    }

    private void registerClientNickname(DataInputStream input, DataOutputStream output)
            throws IOException {

        Action action = Action.of(input.readUTF());
        String nickname = input.readUTF();

        log.debug("processing new nickname registration request for: {} {}", action.label, nickname);

        boolean result = serverService.addNewClient(socket.getPort(), nickname);

        if (result) {
            output.writeUTF(Response.OK.label);
            log.debug("nickname {} correctly registered", nickname);
        } else {
            output.writeUTF(Response.NICKNAME_TAKEN.label);
            log.debug("nickname registration refused");
        }
    }

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            registerClientNickname(input, output);

            while (running) {
                running = false;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
