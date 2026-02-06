package com.github.jroden2.planningpoker.models;

import lombok.Data;

@Data
public class AcceptedScoreMessage {
    private String player;
    private int index;
    private String score;
}
