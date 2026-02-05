package com.github.jroden2.planningpoker.utils;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.UUID;

@Component
public class SecurityUtilities {

    public String generateRoomId() {

        try {
            String input = UUID.randomUUID().toString();

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            String hex = HexFormat.of().formatHex(hash);

            return hex.substring(0, 8);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
