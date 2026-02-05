package com.github.jroden2.planningpoker.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "rooms")
public class Room {

    @Id
    private String id;

    private String tenantId;
    private String name;

    private DeckType deckType;
    private String moderatorName;

    private boolean revealed = false;

    private List<Player> players = new ArrayList<>();

    private List<Story> stories = new ArrayList<>();
    private Story activeStory;

    private Instant createdAt = Instant.now();
    private Instant lastActivity = Instant.now();

}


