package edu.agh.atyranski.client;

import edu.agh.atyranski.model.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClientService {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private final static Logger log = LoggerFactory.getLogger(ClientService.class.getSimpleName());

    private final static String ASCII_ART_MESSAGE_CONTENT =
       """
                      $$ $$$$$ $$
                      $$ $$$$$ $$
                     .$$ $$$$$ $$.
                     :$$ $$$$$ $$:
                     $$$ $$$$$ $$$
                     $$$ $$$$$ $$$
                    ,$$$ $$$$$ $$$.
                   ,$$$$ $$$$$ $$$$.
                  ,$$$$; $$$$$ :$$$$.
                 ,$$$$$  $$$$$  $$$$$.
               ,$$$$$$'  $$$$$  `$$$$$$.
             ,$$$$$$$'   $$$$$   `$$$$$$$.
          ,s$$$$$$$'     $$$$$     `$$$$$$$s.
        $$$$$$$$$'       $$$$$       `$$$$$$$$$
        $$$$$Y'          $$$$$          `Y$$$$$
        """;

    private ClientConfig config;
    private Socket tcpSocket;
    private DatagramSocket udpSocket;
    private MulticastSocket multicastSocket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public ClientService(ClientConfig config) {
        try {
            this.config = config;
            this.tcpSocket = new Socket(config.getAddress(), config.getPort());
            this.udpSocket = new DatagramSocket(tcpSocket.getLocalPort(), config.getAddress());
            this.multicastSocket = new MulticastSocket(config.getMulticastPort());
            multicastSocket.joinGroup(config.getMulticastAddress());
            this.writer = new BufferedWriter(new OutputStreamWriter(tcpSocket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
            registerOnServer();
            log.debug("client instantiated successfully");
            log.debug("tcp-socket [{}:{}] running", tcpSocket.getInetAddress().toString(), tcpSocket.getLocalPort());
            log.debug("udp-socket [{}:{}] running", config.getAddress(), udpSocket.getLocalPort());
            log.debug("multicast-socket [{}:{}] running", multicastSocket.getInetAddress(), multicastSocket.getPort());

        } catch (IOException e) {
            log.warn("client occurred problem while creating socket and out/input streams", e);
            closeConnection();
        }
    }

    private void registerOnServer()
            throws IOException {

        String message = createMessage(config.getNickname(), MessageType.TCP, config.getNickname());
        sendTcpMessage(message);
    }

    private void sendTcpMessage(String message)
            throws IOException {

        writer.write(message);
        writer.newLine();
        writer.flush();
    }

    private void sendUdpMessage(String message)
            throws IOException {

        byte[] sendBuffer = message.getBytes();
        DatagramPacket udpDatagram = new DatagramPacket(
                sendBuffer, sendBuffer.length, config.getAddress(), config.getPort());

        udpSocket.send(udpDatagram);
    }

    private void sendAsciiArt()
            throws IOException {

        String message = createMessage(config.getNickname(), MessageType.ASCII_ART, ASCII_ART_MESSAGE_CONTENT);
        byte[] sendBuffer = message.getBytes();

        DatagramPacket udpDatagram = new DatagramPacket(
                sendBuffer, sendBuffer.length, config.getMulticastAddress(), config.getMulticastPort());
        multicastSocket.send(udpDatagram);
    }

    public void start() {
        listenForTcpMessages();
        listenForUdpMessages();
        listenForAsciiArts();
        launchMessagesSender();
    }

    private String createMessage(String author, MessageType type, String content) {
        return String.format("%s/%s:%s", author, type.value, content);
    }

    public void launchMessagesSender() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (tcpSocket.isConnected()) {
                String userInput = scanner.nextLine();

                MessageType type = getMode(userInput);
                String message = createMessage(config.getNickname(), type, getMessage(userInput));

                switch (type) {
                    case UDP -> sendUdpMessage(message);
                    case ASCII_ART -> sendAsciiArt();
                    default -> sendTcpMessage(message);
                }
            }

        } catch (IOException e) {
            log.warn("occurred problem while sending a message", e);
            closeConnection();
        }
    }

    private MessageType getMode(String userInput) {
        try {
            String messageTypeExtracted = userInput.substring(1, userInput.indexOf(">"));
            return MessageType.of(messageTypeExtracted);
        } catch (StringIndexOutOfBoundsException e) {
            return MessageType.TCP;
        }
    }

    private String getMessage(String userInput) {
        return userInput.substring(userInput.indexOf(">") + 1);
    }

    public void listenForTcpMessages() {
        new Thread(() -> {
            log.debug("TCP message listener running");

            String messageReceived;

            while (tcpSocket.isConnected()) {
                try {
                    messageReceived = reader.readLine();

                    Map<String, String> message = parseReceivedMessage(messageReceived);

                    switch (message.get("author")) {
                        case "logged-in" -> printLoginInfo(message.get("message"));
                        case "logged-out" -> printLogoutInfo(message.get("message"));
                        default -> printMessage(
                                message.get("author"),
                                message.get("type"),
                                message.get("message"));
                    }

                } catch (IOException e) {
                    log.warn("occurred problem while listening for a TCP message from server", e);
                    closeConnection();
                }
            }
            log.debug("TCP message listener stopped");

        }).start();
    }

    public void listenForUdpMessages() {
        new Thread(() -> {
            log.debug("UDP message listener running");
            byte[] receiveBuffer = new byte[1024];

            while (!udpSocket.isClosed()) {
                try {
                    Arrays.fill(receiveBuffer, (byte) 0);
                    DatagramPacket receiveDatagram = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    udpSocket.receive(receiveDatagram);

                    String message = new String(receiveDatagram.getData()).trim();
                    printMessage(message);

                } catch (IOException e) {
                    log.warn("occurred problem while listening for a UDP message from server", e);
                    closeConnection();
                }
            }

            log.debug("UDP message listener stopped");
        }).start();
    }

    public void listenForAsciiArts() {
        new Thread(() -> {
            log.debug("ASCII art listener running");
            byte[] receiveBuffer = new byte[1024];

            while(!multicastSocket.isClosed()) {
                try {
                    Arrays.fill(receiveBuffer, (byte) 0);
                    DatagramPacket receiveDatagram = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    multicastSocket.receive(receiveDatagram);

                    String message = new String(receiveDatagram.getData()).trim();
                    Map<String, String> parsedMessage = parseReceivedMessage(message);

                    if (!parsedMessage.get("author").equals(config.getNickname())) {
                        printMessage(
                                parsedMessage.get("author"),
                                parsedMessage.get("type"),
                                "\n" + parsedMessage.get("message"));
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();
    }

    private Map<String, String> parseReceivedMessage(String message) {
        Map<String, String> parsedMessage = new HashMap<>();

        parsedMessage.put("author", message.substring(0, message.indexOf("/")));
        parsedMessage.put("type", message.substring(message.indexOf("/") + 1, message.indexOf(":")));
        parsedMessage.put("message", message.substring(message.indexOf(":") + 1));

        return parsedMessage;
    }

    private void printMessage(String messageReceived) {
        Map<String, String> parsedMessage = parseReceivedMessage(messageReceived);

        printMessage(
                parsedMessage.get("author"),
                parsedMessage.get("type"),
                parsedMessage.get("message"));
    }

    private void printMessage(String author, String messageType, String message) {
        System.out.printf("%s[%-12s | %s]:%s %s\n", ANSI_GREEN, author, messageType, ANSI_RESET, message);
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

            if (tcpSocket != null) {
                tcpSocket.close();
            }

        } catch (IOException e) {
            log.warn("occurred problem while closing socket and/or out/input streams", e);
        }
    }
}
