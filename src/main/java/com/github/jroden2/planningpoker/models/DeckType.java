package com.github.jroden2.planningpoker.models;

public enum DeckType {
    FIBONACCI("fibonacci"),
    TSHIRT("tshirt");

    String name;

    DeckType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


