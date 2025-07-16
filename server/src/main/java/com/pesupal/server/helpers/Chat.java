package com.pesupal.server.helpers;

public class Chat {

    /**
     * Generates a unique chat ID for a conversation between two users in a specific organization.
     *
     * @param userId1
     * @param userId2
     * @param orgId
     * @return String
     */
    public static String getChatId(Long userId1, Long userId2, Long orgId) {

        return String.format("%d_%d_%d", Math.min(userId1, userId2), Math.max(userId1, userId2), orgId);
    }

    /**
     * Checks if a user is part of a chat based on the chat ID and user ID.
     *
     * @param chatId - userId_userId_orgId
     * @param userId
     * @return Boolean
     */
    public static Boolean isUserInChat(String chatId, Long userId) {
        String[] parts = chatId.split("_");
        if (parts.length < 3) {
            return false; // Invalid chat ID format
        }
        Long userId1 = Long.parseLong(parts[0]);
        Long userId2 = Long.parseLong(parts[1]);
        return userId1.equals(userId) || userId2.equals(userId);
    }
}
