package edu.agh.atyranski.util;

import java.util.HashMap;
import java.util.Map;

public enum Response {

    OK ("ok"),
    NICKNAME_TAKEN ("nickname-taken"),
    INVALID ("invalid");

    private static final Map<String, Response> BY_LABEL = new HashMap<>();

    static {
        for (Response response: values()) {
            BY_LABEL.put(response.label, response);
        }
    }

    public final String label;

    Response(String label) {
        this.label = label;
    }

    public static Response of(String label) {
        if (BY_LABEL.containsKey(label)) {
            return BY_LABEL.get(label);
        }

        return INVALID;
    }
}
