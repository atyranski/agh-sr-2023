package edu.agh.atyranski.client;

import edu.agh.atyranski.util.Action;
import org.slf4j.Logger;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ClientListenerThread extends Thread {

    private final DataInputStream input;
    private final Logger log;
    private final List<String> messages = new LinkedList<>();

    private boolean running = true;

    public ClientListenerThread(DataInputStream input, Logger log) {
        this.input = input;
        this.log = log;
    }

    public void shutdown() {
        System.out.println(messages);
        this.running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Action action = Action.of(input.readUTF());
                String author = input.readUTF();
                String message = input.readUTF();

                log.debug("[{}]: {}", author, message);

                running = false;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
