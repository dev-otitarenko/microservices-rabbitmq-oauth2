package com.maestro.app.utils.types;

public enum TypeAdmin {
    NONE(0),
    SYSTEM_ADMIN(1),
    LOCAL_ADMIN(2);

    private int status;

    TypeAdmin(int status) {
        this.status = status;
    }

    public int getValue() {
        return this.status;
    }
}
