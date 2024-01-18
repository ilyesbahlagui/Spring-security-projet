package com.example.demo.controllers;

import java.sql.Date;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JWTConfig;
import com.example.demo.dto.LoginDto;
import com.example.demo.model.User;
import com.example.demo.service.LoginService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("sessions")
public class SessionCtrl {

    private LoginService loginService;
    private JWTConfig jwtConfig;
    private PasswordEncoder passwordEncoder;

    public SessionCtrl(JWTConfig jwtConfig, LoginService loginService, PasswordEncoder passwordEncoder) {
        this.loginService = loginService;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody LoginDto loginDto) {

        return this.loginService.findByNom(loginDto.getNom())
            .filter(user -> passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
       	 .map(user -> ResponseEntity.ok().header(org.springframework.http.HttpHeaders.SET_COOKIE, buildJWTCookie(user)).build())
            .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

    }
    // TODO
    /**
      * Construit un cookie d'authentification à partir d'un utilisateur fourni.
      *
      * @param user utilisateur connecté.
      * @return cookie sous la forme d'une chaîne de caractères
      */
     private String buildJWTCookie(User user) {


         Keys.secretKeyFor(SignatureAlgorithm.HS512);


         String jetonJWT = Jwts.builder()
             .setSubject(user.getNom())
             .addClaims(Map.of("roles", user.getRoles()))
             .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpireIn() * 1000))
             .signWith(
                 jwtConfig.getSecretKey()
             ).compact();

         ResponseCookie tokenCookie = ResponseCookie.from(jwtConfig.getCookie(), jetonJWT)
             .httpOnly(true)
             .maxAge(jwtConfig.getExpireIn() * 1000)
             .path("/")
             .build();

         return tokenCookie.toString();
     }
 }
