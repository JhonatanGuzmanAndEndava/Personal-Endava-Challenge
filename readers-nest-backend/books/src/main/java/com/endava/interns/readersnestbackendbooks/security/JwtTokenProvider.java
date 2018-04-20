package com.endava.interns.readersnestbackendbooks.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import static com.endava.interns.readersnestbackendbooks.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {


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
}
