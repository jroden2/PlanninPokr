package com.github.jroden2.planningpoker.controllers;

import com.github.jroden2.planningpoker.models.DeckType;
import com.github.jroden2.planningpoker.models.Room;
import com.github.jroden2.planningpoker.models.Story;
import com.github.jroden2.planningpoker.models.dto.StoryScoreDTO;
import com.github.jroden2.planningpoker.services.RoomService;
import com.github.jroden2.planningpoker.utils.PageUtilities;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PlanningController {

    private final RoomService service;

    public PlanningController(RoomService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ModelAndView createPage() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("deckTypes", DeckType.values());
        mav.addObject("pageTitle", PageUtilities.getCreateTitle());
        mav.setViewName("create");
        return mav;
    }

    @PostMapping("/planning/create")
    public ModelAndView createRoom(
            @RequestParam String moderatorName,
            @RequestParam String roomName,
            @RequestParam DeckType deckType
    ) {
        Room room = service.createRoom(roomName, deckType, moderatorName);

        return new ModelAndView("redirect:/planning?id=" + room.getId()
                + "&name=" + moderatorName);
    }

    @GetMapping("/planning/join")
    public ModelAndView joinPage(@RequestParam String id) {
        Room room = service.getRoom(id);
        if (room == null) {
            return new ModelAndView("redirect:/");
        }

        ModelAndView mav = new ModelAndView("join");
        mav.addObject("pageTitle", PageUtilities.getJoinTitle(room.getName()));
        mav.addObject("room", room);

        return mav;
    }

    @GetMapping("/planning")
    public ModelAndView room(
            @RequestParam String id,
            @RequestParam(required = false) String name
    ) {
        Room room = service.getRoom(id);

        if (name == null || name.trim().isEmpty()) {
            return new ModelAndView("redirect:/planning/join?id=" + id);
        }

        ModelAndView mav = new ModelAndView("room");
        mav.addObject("pageTitle", PageUtilities.getRoomTitle(room.getName()));
        mav.addObject("room", room);
        mav.addObject("playerName", name);

        return mav;
    }

    @GetMapping("/planning/scores")
    public ModelAndView scoresPage(
            @RequestParam String id
    ) {
        ModelAndView mav = new ModelAndView();
        Room room = service.getRoom(id);
        if (room == null) {
            return new ModelAndView("redirect:/");
        }

        List<Story> scores = service.getCompletedScores(id);
        mav.addObject("pageTitle", PageUtilities.getScoresRoomTitle(room.getName()));
        mav.addObject("scores", scores);
        mav.addObject("room", room);
        mav.setViewName("scores");
        return mav;
    }

}