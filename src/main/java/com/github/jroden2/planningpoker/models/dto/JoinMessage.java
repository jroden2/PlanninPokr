package com.github.jroden2.planningpoker.models.dto;

public class JoinMessage {

    private String player;

    public JoinMessage() {}

    public JoinMessage(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}

