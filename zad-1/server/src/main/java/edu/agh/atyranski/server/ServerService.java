package edu.agh.atyranski.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerService {

    private final static Logger log = LoggerFactory.getLogger(ServerService.class.getSimpleName());

    private final ServerConfig config;
    private final ServerSocket serverSocket;

    public ServerService(ServerConfig config)
            throws IOException {

        this.config = config;
        this.serverSocket = new ServerSocket(config.getPort(), config.getBacklog(), config.getAddress());
    }

    public void start() {
        try {
            log.info("is running");

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

    public void close() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            log.warn("occurred problem while closing server socket", e);
        }
//        for (ClientSession clientSession: nicknameSessionMap.values()) {
//            try {
//                clientSession.join();
//            } catch (InterruptedException e) {
//                log.warn("occurred problem when closing client session {}", "someName", e); // TODO
//            }
//        }
    }

//    public boolean addNewClient(String nickname, ClientSession socket) {
//        if (nicknameSessionMap.containsKey(nickname)) {
//            log.warn("client tried to login with nickname which already exists");
//            return false;
//        }
//
//        nicknameSessionMap.put(nickname, socket);
//        return true;
//    }

//    public void forwardMessage(String nickname, String message) {
//        for (ClientSession clientSession: nicknameSessionMap.values()) {
//            if (!clientSession.getNickname().equals(nickname)) {
//                clientSession.forwardMessage(nickname, message);
//            }
//        }
//    }
}
