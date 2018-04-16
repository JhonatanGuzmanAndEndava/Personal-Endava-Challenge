package com.endava.interns.readersnestbackendusers.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static com.endava.interns.readersnestbackendusers.security.SecurityConstants.EXPIRATION_TIME;
import static com.endava.interns.readersnestbackendusers.security.SecurityConstants.REFRESH_EXPIRATION_TIME;
import static com.endava.interns.readersnestbackendusers.security.SecurityConstants.SECRET;
import static org.junit.Assert.*;

public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @Before
    public void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
    }

    @Test
    public void testCreateBasicToken(){

        String token =jwtTokenProvider.createToken("1", 0);

        Claims claims = Jwts.parser()
                .setClock(() -> new Date(0))
                .setSigningKey(SECRET)
                .parseClaimsJws(token).getBody();

        assertEquals("1", claims.getSubject());
        assertEquals(EXPIRATION_TIME, claims.getExpiration().getTime());
        assertEquals(false, claims.get("rt"));
    }

    @Test
    public void testCreateRefreshToken(){

        String token =jwtTokenProvider.createRefreshToken("1", 0);

        Claims claims = Jwts.parser()
                .setClock(() -> new Date(0))
                .setSigningKey(SECRET)
                .parseClaimsJws(token).getBody();

        assertEquals("1", claims.getSubject());
        assertEquals(REFRESH_EXPIRATION_TIME, claims.getExpiration().getTime());
        assertEquals(true, claims.get("rt"));
    }

    @Test
    public void testGetUserId(){

        String token = Jwts.builder()
                .setSubject("1")
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();

        String userId = jwtTokenProvider.getUserId(token);

        assertNotNull("It should have returned an userId", userId);
        assertEquals("1", userId);
    }

    @Test
    public void testResolveInvalidHeader(){

        String header = "badheader";

        String token = jwtTokenProvider.resolveToken(header);

        assertNull("Bad header should produce null token", token);
    }

    @Test
    public void testResolveValidHeader(){

        String prefix = "Bearer ";
        String actToken = "token";

        String header = prefix + actToken;

        String token = jwtTokenProvider.resolveToken(header);

        assertNotNull("Header should produce non null token", token);
        assertEquals(actToken, token);
    }

    @Test
    public void testValidToken(){

        String token = Jwts.builder()
                            .signWith(SignatureAlgorithm.HS256, SECRET)
                            .setSubject("1")
                            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                            .claim("rt", false)
                            .compact();

        boolean isValid = jwtTokenProvider.validateToken(token);
        assertTrue("The token should be valid", isValid);
    }

    @Test
    public void testValidTokenWithRefreshToken(){

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .setSubject("1")
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .claim("rt", true)
                .compact();

        boolean isValid = jwtTokenProvider.validateToken(token);
        assertFalse("The token should be invalid", isValid);
    }

    @Test
    public void testValidTokenWithExpiredToken(){

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .setSubject("1")
                .setExpiration(new Date(EXPIRATION_TIME))
                .claim("rt", false)
                .compact();

        boolean isValid = jwtTokenProvider.validateToken(token);
        assertFalse("The token should be invalid", isValid);
    }

    @Test
    public void testValidTokenWithMalformedToken(){

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .setExpiration(new Date(EXPIRATION_TIME))
                .compact();

        boolean isValid = jwtTokenProvider.validateToken(token);
        assertFalse("The token should be invalid", isValid);
    }

    @Test
    public void testValidTokenWithTokenSignedWithDifferentSecret(){

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET + "noise")
                .setExpiration(new Date(EXPIRATION_TIME))
                .claim("rt", false)
                .compact();

        boolean isValid = jwtTokenProvider.validateToken(token);
        assertFalse("The token should be invalid", isValid);
    }

    @Test
    public void testValidRefreshToken(){

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .setSubject("1")
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .claim("rt", true)
                .compact();

        boolean isValid = jwtTokenProvider.validateRefreshToken(token);
        assertTrue("The token should be valid", isValid);
    }

    @Test
    public void testValidRefreshTokenWithToken(){

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .setSubject("1")
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("rt", false)
                .compact();

        boolean isValid = jwtTokenProvider.validateRefreshToken(token);
        assertFalse("The token should be invalid", isValid);
    }

    @Test
    public void testValidRefreshTokenWithExpiredToken(){

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .setSubject("1")
                .setExpiration(new Date(REFRESH_EXPIRATION_TIME))
                .claim("rt", true)
                .compact();

        boolean isValid = jwtTokenProvider.validateRefreshToken(token);
        assertFalse("The token should be invalid", isValid);
    }

    @Test
    public void testValidRefreshTokenWithMalformedToken(){

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .setExpiration(new Date(REFRESH_EXPIRATION_TIME))
                .compact();

        boolean isValid = jwtTokenProvider.validateRefreshToken(token);
        assertFalse("The token should be invalid", isValid);
    }

    @Test
    public void testValidRefreshTokenWithTokenSignedWithDifferentSecret(){

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET + "noise")
                .setExpiration(new Date(REFRESH_EXPIRATION_TIME))
                .claim("rt", true)
                .compact();

        boolean isValid = jwtTokenProvider.validateRefreshToken(token);
        assertFalse("The token should be invalid", isValid);
    }
}