package com.github.jroden2.planningpoker.models.dto;

public class StoryMessage {

    private String player;
    private String title;
    private String url;

    public StoryMessage() {}

    public String getPlayer() {
        return player;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

