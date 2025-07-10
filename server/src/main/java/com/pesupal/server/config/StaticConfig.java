package com.pesupal.server.config;

import com.pesupal.server.enums.Role;

public class StaticConfig {

    public static final int MAXIMUM_TTL_MINUTES = 30;

    public static final int FREE_TRIAL_DAYS = 5;

    public static final String MEDIA_PATH = "/Users/dharma-13910/Videos/";

    public static final int MAX_FILE_SIZE_IN_MB = 5;

    public static boolean eligibleToCreateSubscriptionPlan(Role role) {

        return role.equals(Role.SUPER_ADMIN);
    }
}
