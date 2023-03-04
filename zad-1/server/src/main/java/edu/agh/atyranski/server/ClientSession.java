package edu.agh.atyranski.server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ClientSession extends Thread {

    public static List<ClientSession> clients = new ArrayList<>();
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String nickname;

    public ClientSession(Socket socket) {
        try {
            this.socket = socket;
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            this.nickname = reader.readLine();
            clients.add(this);
            broadcastMessage("logged-in", nickname);

        } catch (IOException e) {
            closeClient();
        }
    }

    private void closeClient() {
        try {
            removeClientHandler();

            if (writer != null) {
                writer.close();
            }

            if (reader != null) {
                reader.close();
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
        broadcastMessage("logged-out", nickname);
    }

    public void broadcastMessage(String author, String message) {
        String messageToSend = String.format("%s:%s", author, message);

        for (ClientSession client: clients) {
            if (!client.getNickname().equals(nickname)) {
                client.forwardMessage(messageToSend);
            }
        }
    }

    private void forwardMessage(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();

        } catch (IOException e) {
            closeClient();
        }
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public void run() {
        String messageReceived;
        String author;
        String message;

        while (!socket.isClosed()) {
            try {
                messageReceived = reader.readLine();
                author = messageReceived.substring(0, messageReceived.indexOf(":"));
                message = messageReceived.substring(messageReceived.indexOf(":") + 1);

                broadcastMessage(author, message);

            } catch (IOException e) {
                closeClient();
                break;
            }
        }
    }
}
