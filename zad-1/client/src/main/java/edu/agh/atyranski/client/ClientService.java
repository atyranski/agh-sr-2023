package edu.agh.atyranski.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientService {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private final static Logger log = LoggerFactory.getLogger(ClientService.class.getSimpleName());

    private ClientConfig config;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public ClientService(ClientConfig config) {
        try {
            this.config = config;
            this.socket = new Socket(config.getAddress(), config.getPort());
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sendMessage(config.getNickname());
            log.debug("client instantiated successfully");

        } catch (IOException e) {
            log.warn("client occurred problem while creating socket and out/input streams", e);
            closeConnection();
        }
    }

    private void sendMessage(String message)
            throws IOException {

        writer.write(message);
        writer.newLine();
        writer.flush();
    }

    public void start() {
        listenForMessages();
        launchMessagesSender();
    }

    public void launchMessagesSender() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (socket.isConnected()) {
                String message = String.format("%s:%s", config.getNickname(), scanner.nextLine());

                sendMessage(message);
            }

        } catch (IOException e) {
            log.warn("occurred problem while sending a message", e);
            closeConnection();
        }
    }

    public void listenForMessages() {
        new Thread(() -> {
            String messageReceived;
            String author;
            String message;

            while (socket.isConnected()) {
                try {
                    messageReceived = reader.readLine();

                    author = messageReceived.substring(0, messageReceived.indexOf(":"));
                    message = messageReceived.substring(messageReceived.indexOf(":") + 1);

                    switch (author) {
                        case "logged-in" -> printLoginInfo(message);
                        case "logged-out" -> printLogoutInfo(message);
                        default -> printMessage(author, message);
                    }

                } catch (IOException e) {
                    log.warn("occurred problem while listening for a message from server", e);
                    closeConnection();
                }
            }
        }).start();
    }
    private void printMessage(String author, String message) {
        System.out.printf("%s[%-12s]:%s %s\n", ANSI_GREEN, author, ANSI_RESET, message);
    }

    private void printLoginInfo(String nickname) {
        System.out.printf("%s[%-12s]:%s %s has entered the chat\n",
                ANSI_YELLOW, "Server", ANSI_RESET, nickname);
    }

    private void printLogoutInfo(String nickname) {
        System.out.printf("%s[%-12s]:%s %s left the chat\n",
                ANSI_YELLOW, "Server", ANSI_RESET, nickname);
    }

    public void close() {
        closeConnection();
        log.debug("client closed");
    }

    private void closeConnection() {
        try {
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
            log.warn("occurred problem while closing socket and/or out/input streams", e);
        }
    }
}
