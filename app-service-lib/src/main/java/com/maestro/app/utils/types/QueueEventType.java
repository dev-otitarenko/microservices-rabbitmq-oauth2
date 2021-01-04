package com.maestro.app.utils.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum QueueEventType {
    SIMPLE_MESSAGE(0),
    CREATE(1),
    UPDATE(2),
    REMOVE(3);

    private int status;

    QueueEventType(int status) {
        this.status = status;
    }

    @JsonValue
    public int getValue() {
        return this.status;
    }
}
