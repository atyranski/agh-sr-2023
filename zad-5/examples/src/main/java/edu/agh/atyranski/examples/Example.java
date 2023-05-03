package edu.agh.atyranski.examples;

import edu.agh.atyranski.examples.models.AdditionAddends;
import edu.agh.atyranski.examples.models.Credentials;
import edu.agh.atyranski.examples.models.Token;

import java.util.Base64;

public class Example {

    private final static String TOKEN_FORMAT = "%s:%s:%s:%s:%s:%d";

    public static String helloWorld() {
        return "Hello world!";
    }

    public static int add(AdditionAddends addends) {
        return addends.getA() + addends.getB();
    }

    public static String encodeTokenBase64(Credentials credentials) {
        final String rawToken = String.format(TOKEN_FORMAT,
                credentials.getAccountType(), credentials.getAccountId(), credentials.getAccountName(),
                credentials.getApiKeyPair().getKey(), credentials.getApiKeyPair().getSecret(),
                credentials.getApiKeyPair().getValidUntil());

        return Base64.getEncoder().encodeToString(rawToken.getBytes());
    }

    public static Credentials decodeTokenBase64(Token token) {
        final byte[] decodedBytes = Base64.getDecoder().decode(token.getToken());
        final String decodedString = new String(decodedBytes);

        final String[] credentials = decodedString.split(":");

        return Credentials.builder()
                .accountId(credentials[1])
                .accountType(Credentials.AccountType.valueOf(credentials[0]))
                .accountName(credentials[2])
                .apiKeyPair(Credentials.APIKeyPair.builder()
                        .key(credentials[3])
                        .secret(credentials[4])
                        .validUntil(Long.parseLong(credentials[5]))
                        .build())
                .build();
    }

}