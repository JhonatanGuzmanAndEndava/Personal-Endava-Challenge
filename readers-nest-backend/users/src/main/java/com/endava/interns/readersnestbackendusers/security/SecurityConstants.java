package com.endava.interns.readersnestbackendusers.security;

public class SecurityConstants {

    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 300_000; // 5 minutes
    public static final long REFRESH_EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String HEADER_STRING = "Authorization";
}
