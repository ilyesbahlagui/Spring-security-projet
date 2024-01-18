package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.LoginRepository;

import jakarta.validation.Valid;

@Service
public class LoginService {

	@Autowired
	LoginRepository loginRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	public User create(@Valid User userToCreate) {
		if (userToCreate.getId() != null) {
			System.out.println("Stop, on veut pas un ID lors de la création");
		}
		userToCreate.setPassword(passwordEncoder.encode(userToCreate.getPassword()));
		userToCreate.setRoles(List.of( "ROLE_USER"));
		return this.loginRepository.save(userToCreate);
	}

	public Optional<User> findByNom(String nom) {
		return this.loginRepository.findByNom(nom);
	}
}
