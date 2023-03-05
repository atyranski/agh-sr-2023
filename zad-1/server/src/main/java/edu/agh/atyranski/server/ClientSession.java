package edu.agh.atyranski.server;

import edu.agh.atyranski.model.MessageType;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ClientSession extends Thread {

    public static List<ClientSession> clients = new ArrayList<>();
    private Socket tcpSocket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String nickname;

    public ClientSession(Socket tcpSocket) {
        try {
            this.tcpSocket = tcpSocket;
            this.writer = new BufferedWriter(new OutputStreamWriter(tcpSocket.getOutputStream(), StandardCharsets.UTF_8));
            this.reader = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream(), StandardCharsets.UTF_8));
            this.nickname = getAuthor(reader.readLine());
            clients.add(this);
            broadcastMessage("logged-in", nickname);

        } catch (IOException e) {
            closeClient();
        }
    }

    private String getAuthor(String message) {
        return message.substring(0, message.indexOf("/"));
    }

    public void removeClientHandler() {
        clients.remove(this);
        broadcastMessage("logged-out", nickname);
    }

    public void broadcastMessage(String author, String content) {
        String messageToSend = createMessage(author, MessageType.TCP, content);

        for (ClientSession client: clients) {
            if (!client.getNickname().equals(nickname)) {
                client.forwardMessage(messageToSend);
            }
        }
    }

    private String createMessage(String author, MessageType type, String content) {
        return String.format("%s/%s:%s", author, type.value, content);
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

    private void closeClient() {
        try {
            removeClientHandler();

            if (writer != null) {
                writer.close();
            }

            if (reader != null) {
                reader.close();
            }

            if (tcpSocket != null) {
                tcpSocket.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        String messageReceived;
        String author;
        String message;

        while (!tcpSocket.isClosed()) {
            try {
                messageReceived = reader.readLine();
                author = messageReceived.substring(0, messageReceived.indexOf("/"));
                message = messageReceived.substring(messageReceived.indexOf(":") + 1);

                broadcastMessage(author, message);

            } catch (IOException e) {
                closeClient();
                break;
            }
        }
    }

    public String getNickname() {
        return nickname;
    }

    public InetAddress getAddres() {
        return tcpSocket.getInetAddress();
    }

    public int getPort() {
        return tcpSocket.getPort();
    }
}
