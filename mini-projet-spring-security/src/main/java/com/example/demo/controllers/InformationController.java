package com.example.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;
import com.example.demo.service.LoginService;

@Controller
@RequestMapping("/information")
public class InformationController {
	@Autowired
	private LoginService loginService;

	@GetMapping
	public String getInformation(Model model,@AuthenticationPrincipal String principal) {
		//Principal contient le nom de l'utilisateur connecté		
		Optional<User> user=loginService.findByNom(principal);
		model.addAttribute("user", user);
		
		return "information_utilisateur";
	}
}