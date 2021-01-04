package com.maestro.app.sample1.ms.events.utils;

public class Constants {
        // Description for document public events
    public static final String QUEUE_LOGSPUBLIC_NAME = "logServiceQueue-public";
    public static final String QUEUE_LOGSPUBLIC_KEY_ROUTES = "events.public.*";
        // Description for document private events
    public static final String QUEUE_LOGSPRIVATE_NAME = "logServiceQueue-private";
    public static final String QUEUE_LOGSPRIVATE_KEY_ROUTES = "events.private.*";
        // Description for a user connect events
    public static final String QUEUE_LOGSCONNECTS_NAME = "logServiceQueue-connects";
    public static final String QUEUE_LOGSCONNECTS_KEY_ROUTES = "events.connects.*";
}
