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

        return String.format("chat_%d_%d_%d", Math.min(userId1, userId2), Math.max(userId1, userId2), orgId);
    }
}
