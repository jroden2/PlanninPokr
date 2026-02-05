package com.github.jroden2.planningpoker.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryMessage {
    private String player;
    private String title;
    private String url;
}

