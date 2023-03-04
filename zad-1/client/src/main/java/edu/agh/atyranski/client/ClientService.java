package edu.agh.atyranski.client;

import edu.agh.atyranski.util.Action;
import edu.agh.atyranski.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientService {
    private final static Logger log = LoggerFactory.getLogger(ClientService.class.getSimpleName());

    private ClientConfig config;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String nickname;

    public ClientService(ClientConfig config, String nickname) {
        try {
            this.config = config;
            this.nickname = nickname;
            this.socket = new Socket(config.getAddress(), config.getPort());
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            log.debug("client instantiated successfully");
        } catch (IOException e) {
            closeConnection(socket, bufferedWriter, bufferedReader);
        }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(nickname);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);

            while (socket.isConnected()) {
                System.out.print("> ");
                String message = scanner.nextLine();

                bufferedWriter.write(nickname + ": " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

        } catch (IOException e) {
            closeConnection(socket, bufferedWriter, bufferedReader);
        }
    }

    public void listenForMessages() {
        new Thread(() -> {
            String messageFromServer;

            while (socket.isConnected()) {
                try {
                    messageFromServer = bufferedReader.readLine();
                    System.out.println(messageFromServer);

                } catch (IOException e) {
                    closeConnection(socket, bufferedWriter, bufferedReader);
                }
            }
        }).start();
    }

    private void closeConnection(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        try {
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

    public void start() {
        listenForMessages();
        sendMessage();
    }

    public void close() {
        closeConnection(socket, bufferedWriter, bufferedReader);
    }


//    private void registerNickname(DataOutputStream output)
//            throws IOException {
//        log.debug("sending request to register nickname");
//
//        output.writeUTF(Action.LOG_IN.label);
//        output.writeUTF(config.getNickname());
//
//        log.debug("successfully logged into server with nickname: {}", config.getNickname());
//    }
//
//    private void sendMessage(DataOutputStream output, String message)
//            throws IOException {
//        log.debug("sending request with message");
//
//        output.writeUTF(Action.MESSAGE_SEND.label);
//        output.writeUTF(message);
//
//        log.debug("successfully send message to server");
//    }
//
//    public void start() {
//        try (Socket socket = new Socket(config.getAddress(), config.getPort());
//             Scanner scanner = new Scanner(System.in);
//             DataInputStream input = new DataInputStream(socket.getInputStream());
//             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
//
//            log.debug("connected to server: {}:{}\n", socket.getInetAddress(), socket.getPort());
//            registerNickname(output);
//
//            ClientListenerThread clientListenerThread = new ClientListenerThread(input, log);
//            clientListenerThread.start();
//
//            while (running) {
//                System.out.print("> ");
//                String message = scanner.nextLine();
//
//                sendMessage(output, message);
//            }
//
//            clientListenerThread.shutdown();
//            clientListenerThread.join();
//
//        } catch (IOException e) {
//            log.warn("occurred problem while creating the socket", e);
//
//        } catch (InterruptedException e) {
//            log.warn("occurred problem while stopping listener thread", e);
//        }
//    }
//
//    public void close() {
//    }
}
