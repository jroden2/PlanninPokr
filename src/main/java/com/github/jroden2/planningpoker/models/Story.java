package com.github.jroden2.planningpoker.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Story {

    private String title;
    private String url;
    private boolean completed = false;

    public Story() {}

    public Story(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() { return title; }
    public String getUrl() { return url; }
    public boolean isCompleted() { return completed; }

    public void setTitle(String title) { this.title = title; }
    public void setUrl(String url) { this.url = url; }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}



