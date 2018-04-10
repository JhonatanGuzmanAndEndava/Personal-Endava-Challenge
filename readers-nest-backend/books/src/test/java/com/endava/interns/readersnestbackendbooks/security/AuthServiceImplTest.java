package com.endava.interns.readersnestbackendbooks.security;

import com.endava.interns.readersnestbackendbooks.exceptions.AuthException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthServiceImplTest {

    private AuthServiceImpl authService;

    private JwtTokenProvider mockedJwtTokenProvider;

    @Before
    public void setUp(){
        mockedJwtTokenProvider = mock(JwtTokenProvider.class);
        authService = new AuthServiceImpl(mockedJwtTokenProvider);
    }

    @Test
    public void checkEmptyHeader() {

        try {
            authService.checkJWT(null, null);
            fail("Didn't throw exception for a null header");
        } catch (AuthException e) {
            assertEquals("authNoToken", e.getError());
        }
    }

    @Test
    public void testInvalidToken() {

        when(mockedJwtTokenProvider.validateToken(anyString())).thenReturn(false);

        try {
            authService.checkJWT(null, "badTokenHeader");
            fail("Didn't throw exception for an invalid header for auth");
        } catch (AuthException e) {
            assertEquals("authInvalidToken", e.getError());
        }
    }

    @Test
    public void testInvalidUserToken() {

        when(mockedJwtTokenProvider.validateToken(nullable(String.class))).thenReturn(true);
        when(mockedJwtTokenProvider.getUserId(anyString())).thenReturn("0");

        try {
            authService.checkJWT("1", "goodTokenHeaderWithWrongUser");
            fail("Didn't throw exception for an invalid user");
        } catch (AuthException e) {
            assertEquals("authNoAuth", e.getError());
        }
    }

    @Test
    public void validToken() {
        when(mockedJwtTokenProvider.validateToken(nullable(String.class))).thenReturn(true);
        when(mockedJwtTokenProvider.getUserId(nullable(String.class))).thenReturn("1");

        try {
            authService.checkJWT("1", "goodTokenHeaderWithWrongUser");
        } catch (AuthException e) {
            fail("There shouldn't been an exception");
        }
    }

}