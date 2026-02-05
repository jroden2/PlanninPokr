package com.github.jroden2.planningpoker.controllers;

import com.github.jroden2.planningpoker.models.SelectStoryMessage;
import com.github.jroden2.planningpoker.models.StoryMessage;
import com.github.jroden2.planningpoker.models.dto.JoinMessage;
import com.github.jroden2.planningpoker.models.dto.ModeratorAction;
import com.github.jroden2.planningpoker.services.RoomService;
import com.github.jroden2.planningpoker.models.Room;
import com.github.jroden2.planningpoker.models.dto.VoteMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PokerSocketController {

    private final RoomService service;

    public PokerSocketController(RoomService service) {
        this.service = service;
    }

    @MessageMapping("/room/{roomId}/join")
    @SendTo("/topic/room/{roomId}")
    public Room join(@DestinationVariable String roomId, JoinMessage msg) {

        return service.joinRoom(roomId, msg.getPlayer());
    }

    @MessageMapping("/room/{roomId}/vote")
    @SendTo("/topic/room/{roomId}")
    public Room vote(@DestinationVariable String roomId,
                     VoteMessage msg) {

        return service.vote(roomId, msg.getPlayer(), msg.getVote());
    }

    @MessageMapping("/room/{roomId}/reveal")
    @SendTo("/topic/room/{roomId}")
    public Room reveal(@DestinationVariable String roomId, ModeratorAction msg) {

        return service.reveal(roomId, msg.getPlayer());
    }

    @MessageMapping("/room/{roomId}/reset")
    @SendTo("/topic/room/{roomId}")
    public Room reset(@DestinationVariable String roomId, ModeratorAction msg) {

        return service.reset(roomId, msg.getPlayer());
    }

    @MessageMapping("/room/{roomId}/disconnect")
    @SendTo("/topic/room/{roomId}")
    public Room disconnect(@DestinationVariable String roomId, JoinMessage msg) {
        return service.disconnect(roomId, msg.getPlayer());
    }

    @MessageMapping("/room/{roomId}/story/add")
    @SendTo("/topic/room/{roomId}")
    public Room addStory(@DestinationVariable String roomId,
                         StoryMessage msg) {

        return service.addStory(
                roomId,
                msg.getPlayer(),
                msg.getTitle(),
                msg.getUrl()
        );
    }

    @MessageMapping("/room/{roomId}/story/select")
    @SendTo("/topic/room/{roomId}")
    public Room selectStory(@DestinationVariable String roomId,
                            SelectStoryMessage msg) {

        return service.selectStory(
                roomId,
                msg.getPlayer(),
                msg.getIndex()
        );
    }

    @MessageMapping("/room/{roomId}/story/complete")
    @SendTo("/topic/room/{roomId}")
    public Room completeStory(@DestinationVariable String roomId,
                              SelectStoryMessage msg) {

        return service.markStoryComplete(
                roomId,
                msg.getPlayer(),
                msg.getIndex()
        );
    }

    @MessageMapping("/room/{roomId}/story/remove")
    @SendTo("/topic/room/{roomId}")
    public Room removeStory(@DestinationVariable String roomId,
                            SelectStoryMessage msg) {

        return service.removeStory(
                roomId,
                msg.getPlayer(),
                msg.getIndex()
        );
    }


}
