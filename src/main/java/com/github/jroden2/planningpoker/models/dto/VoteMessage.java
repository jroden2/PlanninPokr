package com.github.jroden2.planningpoker.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class VoteMessage {

    private String player;
    private String vote;

    public VoteMessage() {}

    public VoteMessage(String player, String vote) {
        this.player = player;
        this.vote = vote;
    }

    public String getPlayer() {
        return player;
    }

    public String getVote() {
        return vote;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}


