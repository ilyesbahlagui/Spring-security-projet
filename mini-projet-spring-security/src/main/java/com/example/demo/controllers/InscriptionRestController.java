package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.LoginService;

import jakarta.validation.Valid;


//Le rest controller retourne des OBJETS
@RestController
@RequestMapping("/log")
public class InscriptionRestController {
	
	@Autowired
	private LoginService loginService;

	@PostMapping("inscription")
	public User create(@RequestBody @Valid User userToCreate) {
		return  this.loginService.create(userToCreate);
	}
}
