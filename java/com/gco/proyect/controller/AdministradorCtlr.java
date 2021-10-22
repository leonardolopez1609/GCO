package com.gco.proyect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gco.proyect.dao.AdministradorDAO;
import com.gco.proyect.model.Administrador;



@Controller
@RequestMapping
public class AdministradorCtlr {

	
	@Autowired
	private AdministradorDAO administradorDao;
	
	
	
	
	@GetMapping("/index")
	public String index(Model model) {
		
		return "index";
	}
	
	@GetMapping("/listar")
	public String listar(Model model) {
		List<Administrador> admins =administradorDao.findAll(); 
		model.addAttribute("administradores", admins);
		return "Plantillas/template";
	}
		
		@GetMapping("/admin/registro")
		public String registarAdmin(Model model) {
			Administrador administrador= new Administrador();
			model.addAttribute("administrador", administrador);
			return "Vistas/registrarAdmin";
		}
	
		 @PostMapping("/admin/guardar")
			public String guardarAdmin(@Validated @ModelAttribute Administrador administrador, BindingResult result,
					Model model, RedirectAttributes attribute) {
			  administradorDao.save(administrador);
				return "index";
			}
		
}

