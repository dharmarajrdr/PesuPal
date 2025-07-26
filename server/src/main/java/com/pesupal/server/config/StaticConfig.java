package com.pesupal.server.config;

import com.pesupal.server.enums.Role;

import java.util.List;

public class StaticConfig {

    public static final int MAXIMUM_TTL_MINUTES = 30;

    public static final int FREE_TRIAL_DAYS = 5;

    public static final int JWT_EXPIRATION_IN_HOURS = 24;

    public static final int PUBLIC_KEY_LENGTH = 12;

    public static final String MEDIA_PATH = "/Users/dharma-13910/Videos/";

    public static final int MAXIMUM_RECENTLY_ACCESSED_FILES = 25;

    public static final int MAX_FILE_SIZE_IN_MB = 5;

    public static final String CLIENT_DOMAIN = "http://localhost:3000";

    public static final String SERVER_DOMAIN = "http://localhost:8080";

    public static final int MAXIMUM_OPTIONS_PER_POLL = 4;

    public static final List<String> HUMAN_RESOURCE_ROLES = List.of("HR", "Human Resource", "Recruiter");

    public static boolean eligibleToCreateSubscriptionPlan(Role role) {

        return role.equals(Role.SUPER_ADMIN);
    }
}
