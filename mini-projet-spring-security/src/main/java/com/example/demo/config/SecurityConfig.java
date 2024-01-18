package com.example.demo.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = false)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,JWTAuthorizationFilter jwtFilter) throws Exception {

        http.authorizeHttpRequests(
        		auth -> auth
        		// TODO  GET /exemples n'est pas soumise à authentification
                .requestMatchers(HttpMethod.GET, "/log/inscription").permitAll()
                .requestMatchers(HttpMethod.GET, "/log/connexion").permitAll()
                .requestMatchers(HttpMethod.POST, "/sessions").permitAll()
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .requestMatchers("/*.html","/*.css","/*.js","/*.json","/img/*.jpg","/img/*.png").permitAll()

                // TODO Les autres requêtes sont soumises à authentification
                .anyRequest().authenticated()
            )
            // TODO configuration CSRF à ajouter
            // Spring Security va valoriser un jeton stocké dans un cookie XSRF-TOKEN
            // Le client souhaitant effectuer une requête de modification (POST par exemple)
            // devra valoriser une entête HTTP "X-XSRF-TOKEN" avec le jeton.
        .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler()::handle)
                // TODO h2-console
                .ignoringRequestMatchers(PathRequest.toH2Console())
            )
            .headers(
                // TODO h2-console
                headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    // TODO
    @Bean
    public PasswordEncoder passwordEncoder() {
        String encodingId = "bcrypt";
        return new DelegatingPasswordEncoder(encodingId, Map.of(encodingId, new BCryptPasswordEncoder()));
    }
}