package com.endava.interns.readersnestbackendbooks.security;


import com.endava.interns.readersnestbackendbooks.exceptions.AuthException;

public interface AuthService {

    void checkJWT(String userId, String authHeader) throws AuthException;
}
