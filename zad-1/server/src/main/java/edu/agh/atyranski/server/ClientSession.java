package edu.agh.atyranski.server;

import edu.agh.atyranski.util.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientSession extends Thread {

    public static List<ClientSession> clients = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String nickname;

    public ClientSession(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nickname = bufferedReader.readLine();
            clients.add(this);
            broadcastMessage("SERVER: " + nickname + " has entered the chat");

        } catch (IOException e) {
            closeClient(socket, bufferedWriter, bufferedReader);
        }
    }



    private void closeClient(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        try {
            removeClientHandler();

            if (bufferedWriter != null) {
                bufferedWriter.close();
            }

            if (bufferedReader != null) {
                bufferedReader.close();
            }

            if (socket != null) {
                socket.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeClientHandler() {
        clients.remove(this);
        broadcastMessage("SERVER: " + nickname + "has left the chat");
    }

    public void broadcastMessage(String message) {
        for (ClientSession client: clients) {
            if (!client.getNickname().equals(nickname)) {
                client.forwardMessage(message);
            }
        }
    }

    private void forwardMessage(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            closeClient(socket, bufferedWriter, bufferedReader);
        }
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public void run() {
        String message;

        while (!socket.isClosed()) {
            try {
                message = bufferedReader.readLine();
                broadcastMessage(message);

            } catch (IOException e) {
                closeClient(socket, bufferedWriter, bufferedReader);
                break;
            }
        }
    }

    //    private final static Logger log = LoggerFactory.getLogger(ClientSession.class.getSimpleName());
//
//    private final ServerService serverService;
//    private final Socket socket;
//    private String nickname;
//    private boolean running = true;
//
//    public ClientSession(ServerService serverService, Socket socket) {
//        this.serverService = serverService;
//        this.socket = socket;
//    }
//
//    public String getNickname() {
//        return nickname;
//    }
//
//    private void registerClientNickname(DataInputStream input)
//            throws IOException {
//        Action action = Action.of(input.readUTF());
//        String nickname = input.readUTF();
//
//        log.debug("processing new nickname registration request for: {} {}", action.label, nickname);
//
//        boolean result = serverService.addNewClient(nickname, this);
//
//        if (result) {
//            this.nickname = nickname;
//            log.debug("nickname {} correctly registered", nickname);
//        } else {
//            log.debug("nickname registration refused");
//        }
//    }
//
//    private void receiveMessage(DataInputStream input)
//            throws IOException {
//        Action action = Action.of(input.readUTF());
//        String message = input.readUTF();
//
//        log.debug("received a {} action from {}", action.label, nickname);
//
//        serverService.forwardMessage(nickname, message);
//
//        log.debug("forwarded message from {}", nickname);
//    }
//
//    public void forwardMessage(String nickname, String message) {
//        try (DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
//            log.debug("forwarding message from {} to {}", nickname, this.nickname);
//
//            output.writeUTF(Action.MESSAGE_FORWARD.label);
//            output.writeUTF(nickname);
//            output.writeUTF(message);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void run() {
//        try (DataInputStream input = new DataInputStream(socket.getInputStream())) {
//
//            registerClientNickname(input);
//
//            while (running) {
//                receiveMessage(input);
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
