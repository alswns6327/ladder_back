package com.ladder.config.jwt;

import com.ladder.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String BEARER_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
            return bearerToken.substring(BEARER_PREFIX.length());
        }

        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);
        String refreshToken = CookieUtil.getCookie(request, tokenProvider.REFRESH_TOKEN);
        if(StringUtils.hasText(jwt) && tokenProvider.validToken(jwt)){
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.setAttribute("ladderAccountId", tokenProvider.getLadderAccountId(jwt));
        }else if(StringUtils.hasText(refreshToken) && tokenProvider.validToken(refreshToken)){
            String auth = String.valueOf(tokenProvider.getClaims(refreshToken).get("auth"));
            String ladderAccountId = tokenProvider.getLadderAccountId(refreshToken);
            String accessToken = tokenProvider.generateToken(ladderAccountId, auth, tokenProvider.ACCESS_TOKEN_EXPIRED);

            Authentication authentication = tokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.setAttribute("ladderAccountId", ladderAccountId);

            response.addHeader("Access-Control-Expose-Headers", "New-Access-Token");
            response.setHeader("New-Access-Token", accessToken);
        }
        filterChain.doFilter(request, response);
    }
}
