package com.github.jroden2.planningpoker.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Story {

    private String title;
    private String url;
    private boolean completed = false;
    private String acceptedScore;

    public Story() {}

    public Story(String title, String url) {
        this.title = title;
        this.url = url;
    }
}



