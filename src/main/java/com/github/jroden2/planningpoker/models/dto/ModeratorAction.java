package com.github.jroden2.planningpoker.models.dto;

public class ModeratorAction {

    private String player;

    public ModeratorAction() {}

    public ModeratorAction(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}

