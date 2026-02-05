package com.github.jroden2.planningpoker.services;

import com.github.jroden2.planningpoker.models.Room;
import com.github.jroden2.planningpoker.repositories.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class HousekeepingService {

    private static final Logger logger = LoggerFactory.getLogger(HousekeepingService.class);
    private final RoomRepository repo;

    public HousekeepingService(RoomRepository repo) {
        this.repo = repo;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void cleanupExpiredRooms() {
        Instant cutoff = Instant.now().minus(48, ChronoUnit.HOURS);
        repo.deleteAll(repo.findByLastActivityBefore(cutoff));
    }
}

