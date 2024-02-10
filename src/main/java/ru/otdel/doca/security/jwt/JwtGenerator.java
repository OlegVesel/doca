package ru.otdel.doca.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtGenerator {
    @Value("${jwt.token.expired}")
    private Long JWT_EXPIRATON;

    @Value("${jwt.token.secret}")
    private String JWT_SECRET;

    public String createToken(Authentication authentication)  {
        String login = authentication.getName();
        Date now = new Date();
        Date expire = new Date(now.getTime() + JWT_EXPIRATON);
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(getSigningKey())
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getLoginFromJWT(String token){
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build();
        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try {
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build();
            parser.parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
//            e.printStackTrace();
//            throw new AuthenticationCredentialsNotFoundException("JWT not correct or expired");
        }
    }

}
