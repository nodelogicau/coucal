package org.coucal;

public class Connector {

    public enum Type {
        CalendarStore, CardStore, AssetStore;
    }

    private String path;

    private Type type;
}
