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

import com.gco.proyect.dao.PacienteDAO;
import com.gco.proyect.model.Paciente;

@Controller
//@RestController
@RequestMapping()
public class PacienteCtlr {

	
	@Autowired
	private PacienteDAO pacienteDAO;
	
	@GetMapping("/paciente/registro")
	public String registarAdmin(Model model) {
		Paciente paciente= new Paciente();
		model.addAttribute("paciente", paciente);
		return "Vistas/registrarPaciente";
	}

	 @PostMapping("/paciente/guardar")
		public String guardarAdmin(@Validated @ModelAttribute Paciente paciente, BindingResult result,
				Model model, RedirectAttributes attribute) {
			 if(!this.existe(paciente)) {
		     if(pacienteDAO.save(paciente) != null) {
			         return "index";
			}}
		      return "Errores/error1";
		}
	 
	 
	 
	 public boolean existe(Paciente p) {
		 List<Paciente> lista = pacienteDAO.findAll();

			for(Paciente pacientes :lista) {
			     if(pacientes.getDocumento().equals( p.getDocumento())) {
			    	 return true;
			    }
			} 
			return false;
	 }
	
	
	
	
}


