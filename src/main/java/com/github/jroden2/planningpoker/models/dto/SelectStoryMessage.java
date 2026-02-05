package com.github.jroden2.planningpoker.models.dto;

public class SelectStoryMessage {

    private String player;
    private int index;

    public SelectStoryMessage() {}

    public String getPlayer() {
        return player;
    }

    public int getIndex() {
        return index;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

