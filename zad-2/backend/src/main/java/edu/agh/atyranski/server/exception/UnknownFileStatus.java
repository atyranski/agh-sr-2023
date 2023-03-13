package edu.agh.atyranski.server.exception;

public class UnknownFileStatus extends RuntimeException {
    public UnknownFileStatus(String status) {
        super(String.format("got unknown file status: %s", status));
    }
}
