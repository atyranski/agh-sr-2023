package edu.agh.atyranski.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ServerService {

    private final static Logger log = LoggerFactory.getLogger(ServerService.class.getSimpleName());
    private final ServerSocket serverSocket;
    private final DatagramSocket serverUdpSocket;

    public ServerService(ServerConfig config)
            throws IOException {

        this.serverUdpSocket = new DatagramSocket(config.getPort(), config.getAddress());
        this.serverSocket = new ServerSocket(config.getPort(), config.getBacklog(), config.getAddress());
    }

    private void handleNewConnections() {
        try {
            log.info("new connections handler running");

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();

                log.debug("new client connected: {}:{}\n", clientSocket.getInetAddress(), clientSocket.getPort());

                ClientSession clientSession = new ClientSession(clientSocket);
                clientSession.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void listenForUdpMessages() {
        new Thread(() -> {
            try {
                log.info("udp listener running");
                byte[] receiveBuffer = new byte[1024];

                while (!serverUdpSocket.isClosed()) {
                    Arrays.fill(receiveBuffer, (byte) 0);
                    DatagramPacket receiveDatagram = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    serverUdpSocket.receive(receiveDatagram);

                    log.debug("udp datagram received successfully");
                    forwardUdpMessage(receiveBuffer);
                }
            } catch (IOException e) {
                log.warn("occurred problem while listening for UDP message from client", e);
            }

        }).start();
    }

    private void forwardUdpMessage(byte[] sendBuffer)
            throws IOException {

        DatagramPacket datagram;
        String author = getAuthor(sendBuffer);

        for (ClientSession client: ClientSession.clients) {
            if (!author.equals(client.getNickname())) {
                datagram = new DatagramPacket(sendBuffer, sendBuffer.length, client.getAddres(), client.getPort());
                serverUdpSocket.send(datagram);
                log.debug("datagram send to {} [{}:{}]", client.getNickname(), client.getAddres(), client.getPort());
            }
        }
    }

    private String getAuthor(byte[] sendBuffer) {
        String message = new String(sendBuffer).trim();

        return message.substring(0, message.indexOf("/"));
    }

    public void start() {
        listenForUdpMessages();
        handleNewConnections();
    }

    public void close() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            log.warn("occurred problem while closing server socket", e);
        }
    }
}
