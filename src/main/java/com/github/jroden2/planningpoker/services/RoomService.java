package com.github.jroden2.planningpoker.services;

import com.github.jroden2.planningpoker.models.*;
import com.github.jroden2.planningpoker.models.dto.StoryScoreDTO;
import com.github.jroden2.planningpoker.repositories.RoomRepository;
import com.github.jroden2.planningpoker.utils.SecurityUtilities;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("roomService")
public class RoomService {

    private final RoomRepository repo;
    private final SecurityUtilities securityUtilities;

    public RoomService(RoomRepository repo, SecurityUtilities securityUtilities) {
        this.repo = repo;
        this.securityUtilities = securityUtilities;
    }

    public Room createRoom(String roomName,
                           DeckType deckType, String moderatorName) {

        Room room = new Room();

        room.setId(securityUtilities.generateRoomId());
        room.setTenantId(roomName);
        room.setName(roomName.trim());
        room.setDeckType(deckType);
        room.setModeratorName(moderatorName.trim());

        return repo.save(room);
    }

    public Room getRoom(String id) {
        return repo.findById(id)
                .orElse(null);
    }

    public Room joinRoom(String roomId, String playerName) {

        Room room = getRoom(roomId);

        String trimmedName = playerName.trim();

        room.getPlayers().removeIf(p -> p.getName().equals(trimmedName));

        room.getPlayers().add(new Player(trimmedName));

        room.setLastActivity(Instant.now());

        return repo.save(room);
    }

    public Room vote(String roomId, String playerName, String vote) {

        Room room = getRoom(roomId);

        String trimmedName = playerName.trim();

        room.getPlayers().forEach(p -> {
            if (p.getName().equals(trimmedName)) {
                p.setVote(vote);
            }
        });

        room.setLastActivity(Instant.now());

        return repo.save(room);
    }

    public Room reveal(String roomId, String playerName) {

        Room room = getRoom(roomId);

        if (!room.getModeratorName().equals(playerName.trim())) {
            throw new RuntimeException("Only moderator can reveal");
        }

        room.setRevealed(true);

        return repo.save(room);
    }

    public Room reset(String roomId, String playerName) {

        Room room = getRoom(roomId);

        if (!room.getModeratorName().equals(playerName.trim())) {
            throw new RuntimeException("Only moderator can reset");
        }

        room.setRevealed(false);

        room.getPlayers().forEach(p -> p.setVote(null));

        return repo.save(room);
    }

    public Room disconnect(String roomId, String playerName) {

        Room room = getRoom(roomId);

        room.getPlayers().removeIf(p -> p.getName().equals(playerName.trim()));

        return repo.save(room);
    }

    public Room addStory(String roomId, String playerName,
                         String title, String url) {

        Room room = getRoom(roomId);

        if (!room.getModeratorName().equals(playerName.trim())) {
            throw new RuntimeException("Only moderator can add stories");
        }

        Story story = new Story(title, url);

        room.getStories().add(story);

        if (room.getActiveStory() == null) {
            room.setActiveStory(story);
        }

        room.setLastActivity(Instant.now());

        return repo.save(room);
    }

    public Room selectStory(String roomId, String playerName, int index) {

        Room room = getRoom(roomId);

        if (!room.getModeratorName().equals(playerName.trim())) {
            throw new RuntimeException("Only moderator can select stories");
        }

        if (index < 0 || index >= room.getStories().size()) {
            throw new RuntimeException("Invalid story index");
        }

        Story selected = room.getStories().get(index);

        room.setActiveStory(selected);

        room.setRevealed(false);

        room.getPlayers().forEach(p -> p.setVote(null));

        room.setLastActivity(Instant.now());

        return repo.save(room);
    }

    public Room markStoryComplete(String roomId, String playerName, int index) {

        Room room = getRoom(roomId);

        if (!room.getModeratorName().equals(playerName.trim())) {
            throw new RuntimeException("Only moderator can complete stories");
        }

        if (index < 0 || index >= room.getStories().size()) {
            throw new RuntimeException("Invalid story index");
        }

        Story story = room.getStories().get(index);
        story.setCompleted(true);

        room.setLastActivity(Instant.now());

        return repo.save(room);
    }

    public Room removeStory(String roomId, String playerName, int index) {

        Room room = getRoom(roomId);

        if (!room.getModeratorName().equals(playerName.trim())) {
            throw new RuntimeException("Only moderator can remove stories");
        }

        if (index < 0 || index >= room.getStories().size()) {
            throw new RuntimeException("Invalid story index");
        }

        Story removed = room.getStories().remove(index);

        if (room.getActiveStory() != null &&
                room.getActiveStory().getTitle().equals(removed.getTitle())) {

            room.setActiveStory(null);

            if (!room.getStories().isEmpty()) {
                room.setActiveStory(room.getStories().get(0));
            }

            room.setRevealed(false);
            room.getPlayers().forEach(p -> p.setVote(null));
        }

        room.setLastActivity(Instant.now());

        return repo.save(room);
    }

    public Room acceptScore(String roomId, String playerName, int index, String score) {

        Room room = getRoom(roomId);

        if (!room.getModeratorName().equals(playerName.trim())) {
            throw new RuntimeException("Only moderator can accept scores");
        }

        if (index < 0 || index >= room.getStories().size()) {
            throw new RuntimeException("Invalid story index");
        }

        Story story = room.getStories().get(index);
        story.setAcceptedScore(score);
        story.setCompleted(true);

        room.setLastActivity(Instant.now());

        return repo.save(room);
    }


    public List<Story> getCompletedScores(String roomId) {
        return repo.findCompletedStoryScores(roomId);
    }


}