package edu.agh.atyranski;

import edu.agh.atyranski.Executors.grpc.ExecutionRequest;
import edu.agh.atyranski.Executors.grpc.ExecutionResponse;
import edu.agh.atyranski.Executors.grpc.ExecutionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.nio.file.Files;
import java.nio.file.Paths;

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
            System.out.println("Response from server: " + response.getData());

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
        final String jarLocation = "C:\\academy\\semester-vi\\sr\\agh-sr-2023\\zad-5\\examples\\target\\examples-1.0.0.jar";
        final String methodName = "edu.agh.atyranski.examples.Example:helloWorld";

        executeFunction(jarLocation, methodName);
    }

    public void executeAdd() {
        final String jarLocation = "C:\\academy\\semester-vi\\sr\\agh-sr-2023\\zad-5\\examples\\target\\examples-1.0.0.jar";
        final String methodName = "edu.agh.atyranski.examples.Example:add";
        final String data = "{\"a\":10,\"b\":15}";

        executeFunction(jarLocation, methodName, data);
    }

    public void executeEncodeTokenBase64() {
        try {
            final String jarLocation = "C:\\academy\\semester-vi\\sr\\agh-sr-2023\\zad-5\\examples\\target\\examples-1.0.0.jar";
            final String methodName = "edu.agh.atyranski.examples.Example:encodeTokenBase64";
            final String jsonDataFilePath = "src/main/resources/credentials-user.json";
            final String data = readFileAsString(jsonDataFilePath);

            executeFunction(jarLocation, methodName, data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void executeDecodeTokenBase64() {
        try {
            final String jarLocation = "C:\\academy\\semester-vi\\sr\\agh-sr-2023\\zad-5\\examples\\target\\examples-1.0.0.jar";
            final String methodName = "edu.agh.atyranski.examples.Example:decodeTokenBase64";
            final String data = "{\"token\":\"VVNFUjoyN2M2MmJhYy1lOWYyLTExZWQtYTA1Yi0wMjQyYWMxMjAwMDM6YXR5cmFuc2tpOkFLSUFJT1NGT0ROTjdFWEFNUExFOndKYWxyWFV0bkZFTUkvSzdNREVORy9iUHhSZmlDWUVYQU1QTEVLRVk6MTY4MzcxNzMwMA\\u003d\\u003d\"}";

            executeFunction(jarLocation, methodName, data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public static void main(String[] args) {
        final ClientApplication clientApplication = new ClientApplication("localhost", 8000);


//        clientApplication.executeHelloWorld();          // Uncomment line to test "Example.helloWorld()" method
//        clientApplication.executeAdd();                 // Uncomment line to test "Example.add(AdditionAddends)" method
//        clientApplication.executeEncodeTokenBase64();   // Uncomment line to test "Example.encodeTokenBase64(Credentials)" method
//        clientApplication.executeDecodeTokenBase64();   // Uncomment line to test "Example.decodeTokenBase64(Token)" method

        clientApplication.shutdown();
    }
}
