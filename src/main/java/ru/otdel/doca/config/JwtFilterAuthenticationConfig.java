package ru.otdel.doca.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otdel.doca.security.jwt.CustomUserDetailsService;
import ru.otdel.doca.security.jwt.JwtFilterAuthentication;
import ru.otdel.doca.security.jwt.JwtGenerator;

@Configuration
@RequiredArgsConstructor
public class JwtFilterAuthenticationConfig {
    private final JwtGenerator jwtGenerator;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public JwtFilterAuthentication jwtFilterAuthentication(){
        return new JwtFilterAuthentication(jwtGenerator, customUserDetailsService);
    }
}
