package com.ladder.config.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class TokenProvider {

    private final JwtPropeties jwtPropeties;

    public final static Duration ACCESS_TOKEN_EXPIRED = Duration.ofHours(2);
    public final static Duration REFRESH_TOKEN_EXPIRED = Duration.ofDays(14);

    public final String ACCESS_TOKEN = "access_token";
    public final String REFRESH_TOKEN = "refresh_token";

    public String generateToken(String ladderAccountId, String ladderAccountAuth, Duration expiredAt){
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), ladderAccountId, ladderAccountAuth);
    }

    private String makeToken(Date expiry, String ladderAccountId, String ladderAccountAuth) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtPropeties.getIssuer())
                .setExpiration(expiry)
                .setSubject(ladderAccountId)
                .claim("id", ladderAccountId)
                .claim("auth", ladderAccountAuth)
                .signWith(SignatureAlgorithm.HS256, jwtPropeties.getSecretKey())
                .compact();
    }

    public Authentication getAuthentication(String token){
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorties = Collections.singleton(claims.get("auth", SimpleGrantedAuthority.class));
        return new UsernamePasswordAuthenticationToken(new User(claims.getSubject(), "", authorties), token, authorties);
    }

    public boolean validToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(jwtPropeties.getSecretKey())
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String getLadderAccountId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", String.class);
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtPropeties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

}
