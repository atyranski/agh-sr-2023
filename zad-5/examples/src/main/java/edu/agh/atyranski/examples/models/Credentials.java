package edu.agh.atyranski.examples.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {
    private String accountName;
    private String accountId;
    private AccountType accountType;
    private APIKeyPair apiKeyPair;

    public enum AccountType {
        ADMIN,
        USER,
        GUEST
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class APIKeyPair {
        private String key;
        private String secret;
        private long validUntil;
    }
}

