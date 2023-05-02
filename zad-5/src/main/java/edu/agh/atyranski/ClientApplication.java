package edu.agh.atyranski;

import edu.agh.atyranski.Executors.grpc.ExecutionRequest;
import edu.agh.atyranski.Executors.grpc.ExecutionResponse;
import edu.agh.atyranski.Executors.grpc.ExecutionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class ClientApplication {

    private final ManagedChannel channel;
    private final ExecutionServiceGrpc.ExecutionServiceBlockingStub stub;

    private ClientApplication(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.stub = ExecutionServiceGrpc.newBlockingStub(channel);
    }

    private void executeFunction(String jarLocation, String methodName, String data) {
        try {
            ExecutionRequest request = ExecutionRequest.newBuilder()
                    .setJarLocation(jarLocation)
                    .setMethod(methodName)
                    .setData(data)
                    .build();

            ExecutionResponse response = stub.execute(request);
            System.out.println("Response from server: " + response);

        } catch (StatusRuntimeException e) {
            System.out.println("RPC failed: {0} "+e.getStatus());
        }
    }

    private void executeFunction(String jarLocation, String methodName) {
        try {
            ExecutionRequest request = ExecutionRequest.newBuilder()
                    .setJarLocation(jarLocation)
                    .setMethod(methodName)
                    .build();

            ExecutionResponse response = stub.execute(request);
            System.out.println("Response from server: " + response);

        } catch (StatusRuntimeException e) {
            System.out.println("RPC failed: {0} "+e.getStatus());
        }
    }

    private void shutdown() {
        channel.shutdown();
    }

    public void executeHelloWorld() {
        final String jarLocation = "C:\\academy\\semester-vi\\sr\\zad-5\\examples\\target\\examples-1.0.0.jar";
        final String methodName = "edu.agh.atyranski.examples.Example:helloWorld";

        executeFunction(jarLocation, methodName);
    }

    public void executeAdd() {
        final String jarLocation = "C:\\academy\\semester-vi\\sr\\zad-5\\examples\\target\\examples-1.0.0.jar";
        final String methodName = "edu.agh.atyranski.examples.Example:add";
        final String data = "{\"a\":10,\"b\":15}";

        executeFunction(jarLocation, methodName, data);
    }

    public static void main(String[] args) {
        final ClientApplication clientApplication = new ClientApplication("localhost", 8000);

        clientApplication.executeAdd();

        clientApplication.shutdown();
    }
}
