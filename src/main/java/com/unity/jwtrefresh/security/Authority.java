package com.unity.jwtrefresh.security;

public class Authority {

    public static final String[] USER_AUTHORITIES = {"user:read"};
    public static final String[] ADVOCATE_AUTHORITIES = {"user:read", "adv:match-making"};
    public static final String[] SPEAKER_AUTHORITIES = {"speaker:read"};
    public static final String[] ADMIN_AUTHORITIES = {"user:read", "user:create", "user:update", "user:disable"};
    public static final String[] SUPER_ADMIN_AUTHORITIES = {"user:read", "user:create", "user:update", "user:delete"};


}
