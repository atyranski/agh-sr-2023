package edu.agh.atyranski.client.exception;

public class MissingClientNicknameException extends RuntimeException {

    public MissingClientNicknameException() {
        super("nickname is required");
    }
}
