package com.github.jroden2.planningpoker.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Player {

    private String name;
    private String vote;
    private boolean connected = true;

    public Player() {}

    public Player(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public String getVote() { return vote; }
    public boolean isConnected() { return connected; }

    public void setName(String name) { this.name = name; }
    public void setVote(String vote) { this.vote = vote; }
    public void setConnected(boolean connected) { this.connected = connected; }
}


