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
    private String nickname;
    private boolean running = true;

    public ClientSession(ServerService serverService, Socket socket) {
        this.serverService = serverService;
        this.socket = socket;
    }

    public String getNickname() {
        return nickname;
    }

    private void registerClientNickname(DataInputStream input)
            throws IOException {
        Action action = Action.of(input.readUTF());
        String nickname = input.readUTF();

        log.debug("processing new nickname registration request for: {} {}", action.label, nickname);

        boolean result = serverService.addNewClient(nickname, this);

        if (result) {
            this.nickname = nickname;
            log.debug("nickname {} correctly registered", nickname);
        } else {
            log.debug("nickname registration refused");
        }
    }

    private void receiveMessage(DataInputStream input)
            throws IOException {
        Action action = Action.of(input.readUTF());
        String message = input.readUTF();

        log.debug("received a {} action from {}", action.label, nickname);

        serverService.forwardMessage(nickname, message);

        log.debug("forwarded message from {}", nickname);
    }

    public void forwardMessage(String nickname, String message) {
        try (DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            log.debug("forwarding message from {} to {}", nickname, this.nickname);

            output.writeUTF(Action.MESSAGE_FORWARD.label);
            output.writeUTF(nickname);
            output.writeUTF(message);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream())) {

            registerClientNickname(input);

            while (running) {
                receiveMessage(input);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
