package com.gco.proyect.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gco.proyect.model.Sesion;

@Controller
@RequestMapping
public class LoginCtrl {
	

	
	
	@GetMapping("/loginPaciente")
	public String loginPaciente(Model model) {
		Sesion sesion=new Sesion();
		model.addAttribute("sesion", sesion);
		return "Vistas/loginPaciente";
	}
	
	@GetMapping("/loginAdmin")
	public String loginAdmin(Model model) {
		Sesion sesion=new Sesion();
		model.addAttribute("sesion", sesion);
		return "Vistas/loginAdmin";
	}
	
	
	
	
}
