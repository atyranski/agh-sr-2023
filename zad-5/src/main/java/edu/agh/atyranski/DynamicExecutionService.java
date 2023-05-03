package edu.agh.atyranski;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.agh.atyranski.Executors.grpc.ExecutionResponse;
import edu.agh.atyranski.Executors.grpc.ExecutionServiceGrpc;
import edu.agh.atyranski.Executors.grpc.ExecutorService;
import io.grpc.Status;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;


public class DynamicExecutionService extends ExecutionServiceGrpc.ExecutionServiceImplBase {

    @Override
    public void execute(edu.agh.atyranski.Executors.grpc.ExecutionRequest request,
                        io.grpc.stub.StreamObserver<edu.agh.atyranski.Executors.grpc.ExecutionResponse> responseObserver) {

        System.out.println(request);

        final String[] methodParts = request.getMethod().split(":");
        final String methodName = methodParts[1];

        ClassLoader classLoader = ExecutorService.class.getClassLoader();
        Path jarPath = Paths.get(request.getJarLocation());
        ExecutionResponse.Builder responseBuilder = ExecutionResponse.newBuilder();

        try (URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{jarPath.toUri().toURL()}, classLoader)) {
            Class<?> executableClass = urlClassLoader.loadClass(methodParts[0]);
            Method method = null;
            Method[] methods = executableClass.getDeclaredMethods();
            Object result = null;
            Object object = executableClass.newInstance();
            Gson gson = new GsonBuilder().setLenient().create();
            boolean methodFound = false;

            for (Method m: methods) {
                if (m.getName().equals(methodName)) {
                    methodFound = true;
                    Class<?>[] parameterTypes = m.getParameterTypes();

                    if (parameterTypes.length == 1) {
                        Class<?> parameterType = parameterTypes[0];
                        method = executableClass.getMethod(methodName, parameterType);
                        result = method.invoke(object, gson.fromJson(request.getData(), parameterType));
                        break;
                    } else if (parameterTypes.length == 0) {
                        method = executableClass.getMethod(methodName);
                        result = method.invoke(object);
                        break;
                    }
                }
            }

            if (methodFound) {
                if (result == null) {
                    responseBuilder.build();
                } else {
                    responseBuilder.setData(gson.toJson(result)).build();
                }

                responseObserver.onNext(responseBuilder.build());
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(Status.UNKNOWN.augmentDescription("Provided method don't exist")
                        .asRuntimeException());
            }

        } catch (MalformedURLException e) {
            responseObserver.onError(Status.UNKNOWN.augmentDescription("Malformed url")
                    .asRuntimeException());
            e.printStackTrace();
        } catch (IOException e) {
            responseObserver.onError(Status.UNKNOWN.augmentDescription("IOException")
                    .asRuntimeException());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            responseObserver.onError(Status.UNKNOWN.augmentDescription("No such class")
                    .asRuntimeException());
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            responseObserver.onError(Status.UNKNOWN.augmentDescription("There is no such method")
                    .asRuntimeException());
            e.printStackTrace();
        } catch (InstantiationException e) {
            responseObserver.onError(Status.UNKNOWN.augmentDescription("Instantiation exception")
                    .asRuntimeException());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            responseObserver.onError(Status.UNKNOWN.augmentDescription("Illegal access")
                    .asRuntimeException());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            responseObserver.onError(Status.UNKNOWN.augmentDescription("Could not invoke method").asRuntimeException());
            e.printStackTrace();
        }
    }
}
