package com.github.jroden2.planningpoker.repositories;

import com.github.jroden2.planningpoker.models.Room;
import com.github.jroden2.planningpoker.models.Story;
import com.github.jroden2.planningpoker.models.dto.StoryScoreDTO;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository("roomRepository")
public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room> findByLastActivityBefore(Instant cutoff);

    @Aggregation(pipeline = {
            "{ $match: { _id: ?0 } }",
            "{ $unwind: '$stories' }",
            "{ $match: { 'stories.completed': true } }",
            "{ $replaceRoot: { newRoot: '$stories' } }"
    })
    List<Story> findCompletedStoryScores(String roomId);
}


