package com.endava.interns.readersnestbackendbookclubs.security;

import com.endava.interns.readersnestbackendbookclubs.exceptions.AuthException;

public interface AuthService {

    void checkJWT(String userId, String authHeader) throws AuthException;
}
