package edu.agh.atyranski.model;

import java.util.HashMap;
import java.util.Map;

public enum MessageType {
    UDP ("udp"),
    TCP ("tcp"),
    ASCII_ART ("ascii-art"),
    INVALID ("");

    private static final Map<String, MessageType> BY_LABEL = new HashMap<>();

    static {
        for (MessageType messageType: values()) {
            BY_LABEL.put(messageType.value, messageType);
        }
    }

    public final String value;

    MessageType(String value) {
        this.value = value;
    }

    public static MessageType of(String value) {
        if (BY_LABEL.containsKey(value)) {
            return BY_LABEL.get(value);
        }

        return INVALID;
    }

    public String value() {
        return this.value;
    }
}
