package com.example.demo.config;


import java.security.Key;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;

@Configuration
public class JWTConfig {

    @Value("${jwt.expires_in}")
    private long expireIn;

    @Value("${jwt.cookie}")
    private String cookie;

    @Value("${jwt.secret}")
    private String secret;

    private Key secretKey;

    @PostConstruct
    public void buildKey() {
        secretKey = new SecretKeySpec(Base64.getDecoder().decode(getSecret()),
            SignatureAlgorithm.HS256.getJcaName());
    }

	/**
	 * @return the expireIn
	 */
	public long getExpireIn() {
		return expireIn;
	}

	/**
	 * @param expireIn the expireIn to set
	 */
	public void setExpireIn(long expireIn) {
		this.expireIn = expireIn;
	}

	/**
	 * @return the cookie
	 */
	public String getCookie() {
		return cookie;
	}

	/**
	 * @param cookie the cookie to set
	 */
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	/**
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * @param secret the secret to set
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * @return the secretKey
	 */
	public Key getSecretKey() {
		return secretKey;
	}

	/**
	 * @param secretKey the secretKey to set
	 */
	public void setSecretKey(Key secretKey) {
		this.secretKey = secretKey;
	}

    // TODO Getters / Setters
}
