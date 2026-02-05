package com.github.jroden2.planningpoker.repositories;

import com.github.jroden2.planningpoker.models.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository("roomRepository")
public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room> findByLastActivityBefore(Instant cutoff);
}


