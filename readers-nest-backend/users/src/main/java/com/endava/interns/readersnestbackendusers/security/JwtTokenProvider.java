package com.endava.interns.readersnestbackendusers.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.endava.interns.readersnestbackendusers.security.SecurityConstants.EXPIRATION_TIME;
import static com.endava.interns.readersnestbackendusers.security.SecurityConstants.REFRESH_EXPIRATION_TIME;
import static com.endava.interns.readersnestbackendusers.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {

    public String createToken(String userId, long issuingTime) {

        JwtBuilder partialToken = buildBasicToken(userId, issuingTime ,EXPIRATION_TIME);
        return finishToken(partialToken.claim("rt", false));
    }

    public String createRefreshToken(String userId, long issuingTime){

        JwtBuilder partialToken = buildBasicToken(userId,issuingTime, REFRESH_EXPIRATION_TIME);
        return finishToken(partialToken.claim("rt", true));
    }


    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET)
                    .require("rt", false)
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String token){

        try {
            Jwts.parser().setSigningKey(SECRET)
                    .require("rt", true)
                    .parseClaimsJws(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    private JwtBuilder buildBasicToken(String userId,long now_time ,long expiration_time){
        Claims claims = Jwts.claims().setSubject(userId);

        Date expiration = getExpiration(now_time, expiration_time);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration);
    }

    private Date getExpiration(long act_time, long valid_time){

        long exp = act_time + valid_time;

        return new Date(exp);
    }

    private String finishToken(JwtBuilder builder){
        return builder.signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

}
