package de.adorsys.opba.fintech.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors().configurationSource(corsConfigurationSource())
            .and()
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/v1/**", "/fintech-api-proxy/v1/**").permitAll()
            .anyRequest().permitAll()
            .and()
            .httpBasic().disable()
            .formLogin().disable();
        
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList(
            "Content-Type",
            "X-Request-ID",
            "X-XSRF-TOKEN",
            "X-SESSION-MAX-AGE",
            "X-REDIRECT-MAX-AGE",
            "Authorization",
            "X-Fintech-ID",
            "x-session-id",
            "x-timestamp"
        ));
        configuration.setExposedHeaders(Arrays.asList(
            "X-XSRF-TOKEN",
            "X-SESSION-MAX-AGE",
            "X-REDIRECT-MAX-AGE",
            "X-REQUEST-ID",
            "Set-Cookie"
        ));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
} 