package com.ladder.config;

import com.ladder.config.jwt.JwtFilter;
import com.ladder.config.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final ClientInfoProperties clientInfoProperties;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers(
                        "/account"
                        , "/account/{userId:.*}"
                        , "/login"
                        , "/refreshToken"
                        , "/logout"
                ).permitAll()
                .requestMatchers(
                        HttpMethod.GET
                        , "/account/list"
                        , "/book/info/list"
                        , "/book/info/{bookInfoId:\\d+}"
                        , "/book/chapter/list"
                        , "/book/chapter/{bookChapterInfoId:\\d+}"
                ).permitAll()
//                .requestMatchers("/logout").hasAuthority("USER")
                .requestMatchers(
                        HttpMethod.GET
                        , "/article/category/list"
                        , "/article"
                        , "/article/{articleSeq}"
                ).permitAll()
                .requestMatchers(
                        HttpMethod.POST
                        ,"/article/category"
                        , "/article/category"
                        , "/article/category/{categorySeq:\\d+}"
                        , "/article/sub-category"
                        , "/article/sub-category"
                        , "/article/sub-category/{categorySeq:\\d+}"
                        , "/article"
                        , "/article"
                        , "/article/{articleSeq:\\d+}"
                        , "/book/info"
                        , "/book/info"
                        , "/book/info/{bookInfoId:\\d+}"
                        , "/book/chapter/content"
                        , "/book/chapter/content"
                        , "/book/chapter/{bookChapterInfoId:\\d+}"
                ).hasAnyAuthority("USER", "STUDENT", "ADMIN")
                .requestMatchers(
                        HttpMethod.PUT
                        ,"/article/category"
                        , "/article/category"
                        , "/article/category/{categorySeq:\\d+}"
                        , "/article/sub-category"
                        , "/article/sub-category"
                        , "/article/sub-category/{categorySeq:\\d+}"
                        , "/article"
                        , "/article"
                        , "/article/{articleSeq:\\d+}"
                        , "/book/info"
                        , "/book/info"
                        , "/book/info/{bookInfoId:\\d+}"
                        , "/book/chapter/content"
                        , "/book/chapter/content"
                        , "/book/chapter/{bookChapterInfoId:\\d+}"
                ).hasAnyAuthority("USER", "STUDENT", "ADMIN")
                .requestMatchers(
                        HttpMethod.DELETE
                        ,"/article/category"
                        , "/article/category"
                        , "/article/category/{categorySeq:\\d+}"
                        , "/article/sub-category"
                        , "/article/sub-category"
                        , "/article/sub-category/{categorySeq:\\d+}"
                        , "/article"
                        , "/article"
                        , "/article/{articleSeq:\\d+}"
                        , "/book/info"
                        , "/book/info"
                        , "/book/info/{bookInfoId:\\d+}"
                        , "/book/chapter/content"
                        , "/book/chapter/content"
                        , "/book/chapter/{bookChapterInfoId:\\d+}"
                ).hasAnyAuthority("USER", "STUDENT", "ADMIN")
                .requestMatchers(
                        HttpMethod.GET
                        , "/edu/category/list"
                        , "/edu"
                        , "/edu/{eduSeq:\\d+}"
                ).hasAuthority("STUDENT")
                .requestMatchers(
                        HttpMethod.POST
                        , "/edu/category"
                        , "/edu/category"
                        , "/edu/category/{categorySeq:\\d+}"
                        , "/edu/sub-category"
                        , "/edu/sub-category"
                        , "/edu/sub-category/{categorySeq:\\d+}"
                        , "/edu"
                        , "/edu"
                        , "/edu/{eduSeq:\\d+}"
                ).hasAuthority("ADMIN")
                .requestMatchers(
                        HttpMethod.PUT
                        , "/edu/category"
                        , "/edu/category"
                        , "/edu/category/{categorySeq:\\d+}"
                        , "/edu/sub-category"
                        , "/edu/sub-category"
                        , "/edu/sub-category/{categorySeq:\\d+}"
                        , "/edu"
                        , "/edu"
                        , "/edu/{eduSeq:\\d+}"
                ).hasAuthority("ADMIN")
                .requestMatchers(
                        HttpMethod.DELETE
                        , "/edu/category"
                        , "/edu/category"
                        , "/edu/category/{categorySeq:\\d+}"
                        , "/edu/sub-category"
                        , "/edu/sub-category"
                        , "/edu/sub-category/{categorySeq:\\d+}"
                        , "/edu"
                        , "/edu"
                        , "/edu/{eduSeq:\\d+}"
                ).hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout().disable()
                .formLogin().disable()
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint).accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(clientInfoProperties.getAllowedOrigins()));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
