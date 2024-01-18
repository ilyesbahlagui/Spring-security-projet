package com.example.demo.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;
import com.example.demo.service.LoginService;

@Component
public class OnStartup {

	private LoginService LoginService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public OnStartup(LoginService LoginService) {
		this.LoginService = LoginService;
	}

	@EventListener(ContextRefreshedEvent.class)
	public void init() {

		// insertion de 2 utilisateurs en base de données
		LoginService.create(new User("toto","toto", List.of("ROLE_ADMIN", "ROLE_USER")));
		LoginService.create(new User("tata", "tata", List.of("ROLE_USER")));

	}
}
