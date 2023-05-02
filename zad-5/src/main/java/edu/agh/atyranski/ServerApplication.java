package edu.agh.atyranski;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ServerApplication {

    private void start() {
        try {
            final Server server = ServerBuilder.forPort(8000)
                    .addService(new DynamicExecutionService())
                    .build();

            server.start();
            System.out.println("Server started");
            server.awaitTermination();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Server stopped");
        }
    }

    public static void main(String[] args) {
        final ServerApplication server = new ServerApplication();
        server.start();
    }
}
