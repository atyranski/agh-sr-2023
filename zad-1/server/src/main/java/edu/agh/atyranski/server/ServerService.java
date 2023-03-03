package edu.agh.atyranski.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ServerService {

    private final static Logger log = LoggerFactory.getLogger(ServerService.class.getSimpleName());

    private final ServerConfig config;
    private final Map<String, Integer> nicknamePortMap = new HashMap<>();
    final private List<ClientSession> clientSessionList = new LinkedList<>();

    private boolean running = true;


    public ServerService(ServerConfig config) {
        this.config = config;
    }

    public void start() {
        try (ServerSocket socket = new ServerSocket(config.getPort(), config.getBacklog(), config.getAddress())) {
            log.info("is running");

            while (running) {
                Socket clientSocket = socket.accept();

                log.debug("new client connected: {}:{}\n", clientSocket.getInetAddress(), clientSocket.getPort());

                ClientSession clientSession = new ClientSession(this, clientSocket);
                clientSession.start();
                clientSessionList.add(clientSession);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        for (ClientSession clientSession: clientSessionList) {
            try {
                clientSession.join();
            } catch (InterruptedException e) {
                log.warn("occurred problem when closing client session {}", "someName", e); // TODO
            }
        }
    }

    public boolean addNewClient(int port, String nickname) {
        if (nicknamePortMap.containsKey(nickname)) {
            log.warn("client tried to login with nickname which already exists");
            return false;
        }

        nicknamePortMap.put(nickname, port);
        return true;
    }
}
