package com.endava.interns.readersnestbackendusers.security;

import com.endava.interns.readersnestbackendusers.exceptions.AuthException;

public interface AuthService {

    void checkJWT(String userId, String authHeader) throws AuthException;
    String refreshToken(String refreshToken, long issueTime) throws AuthException;
}
