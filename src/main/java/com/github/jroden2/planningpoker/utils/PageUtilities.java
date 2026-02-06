package com.github.jroden2.planningpoker.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PageUtilities {

    private static String company;

    public PageUtilities(@Value("${spring.application.name}") String companyName) {
        company = companyName;
    }

    private static final String JOIN_TITLE_TEMPLATE = "Join the %s planning poker room | %s";

    public static String getJoinTitle(String roomName) {
        return String.format(JOIN_TITLE_TEMPLATE, roomName, company);
    }

    public static String getRoomTitle(String roomName) {
        return String.format("%s | %s", roomName, company);
    }

    public static String getScoresRoomTitle(String roomName) {
        return String.format("Accepted Scores | %s | %s", roomName, company);
    }

    public static String getCreateTitle() {
        return String.format("Create a planning poker room | %s", company);
    }
}
