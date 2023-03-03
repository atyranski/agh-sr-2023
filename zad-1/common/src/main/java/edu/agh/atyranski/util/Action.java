package edu.agh.atyranski.util;

import java.util.HashMap;
import java.util.Map;

public enum Action {

    LOG_IN ("login"),
    LOG_OUT ("logout"),
    MESSAGE_SEND ("message-send"),
    MESSAGE_FORWARD ("message-forward"),
    INVALID ("invalid");

    private static final Map<String, Action> BY_LABEL = new HashMap<>();

    static {
        for (Action action: values()) {
            BY_LABEL.put(action.label, action);
        }
    }

    public final String label;

    Action(String label) {
        this.label = label;
    }

    public static Action of(String label) {
        if (BY_LABEL.containsKey(label)) {
            return BY_LABEL.get(label);
        }

        return INVALID;
    }
}
