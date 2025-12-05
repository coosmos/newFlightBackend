package com.app.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http

                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // configure which endpoints need authentication
                .authorizeExchange(exchanges -> exchanges
                        // allow signup and login without authentication
                        .pathMatchers("/api/auth/**").permitAll()

                        // all other endpoints require authentication
                        .anyExchange().authenticated()
                                //.anyExchange().permitAll()
                )

                .build();
    }
}