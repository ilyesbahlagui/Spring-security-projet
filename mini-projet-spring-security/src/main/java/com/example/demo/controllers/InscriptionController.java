package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//Le rest controller retourne des VUS
@Controller
@RequestMapping("/log")
public class InscriptionController {
	



	@GetMapping("inscription")
	public String getInscription(Model model) {
		return  "inscription";
	}
	
	@GetMapping("connexion")
	public String getConnexion(Model model) {
		return  "connexion";
	}
}
