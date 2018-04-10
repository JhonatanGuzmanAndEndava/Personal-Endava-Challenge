package com.endava.interns.readersnestbackendbooks.security;

import com.endava.interns.readersnestbackendbooks.exceptions.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void checkJWT(String userId, String authHeader) throws AuthException {

        if(authHeader == null) throw  new AuthException("authNoToken", "Not authorized" +
                " to perform this action");

        String token = jwtTokenProvider.resolveToken(authHeader);
        if(!jwtTokenProvider.validateToken(token))
            throw new AuthException("authInvalidToken", "Invalid token");

        if(!userId.equals(jwtTokenProvider.getUserId(token)))
            throw new AuthException("authNoAuth", "Can't perform this action");

    }
}
