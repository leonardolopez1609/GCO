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
import com.gco.proyect.model.Sesion;



@Controller
@RequestMapping
public class AdministradorCtlr {

	
	@Autowired
	private AdministradorDAO administradorDao;
	
	
	
	
	
	
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
			  if(administradorDao.save(administrador)!=null) {
					model.addAttribute("usuario", administrador.getNombre());
					attribute.addFlashAttribute("success", "Registro Exitoso");
					return "redirect:/loginAdmin";
			  }
			  attribute.addFlashAttribute("error", "Por favor verifique los datos");
				return "redirect:/admin/registro";
			}
		 
		 @PostMapping("/ingresoAdmin")
			public String ingresoPaciente(@Validated @ModelAttribute Sesion sesion, BindingResult result, Model model,
					RedirectAttributes attribute) {
				if (this.login(sesion)!=null) {
					model.addAttribute("usuario",administradorDao.findById(this.login(sesion)).get().getNombre());
					return "index";
				} else
					attribute.addFlashAttribute("error", "Usuario o contraseña no válidos");
					return "redirect:/loginAdmin";
			}
		 
		 
		 public boolean existe(Administrador a) {
				List<Administrador> lista = administradorDao.findAll();

				for (Administrador pacientes : lista) {
					if (pacientes.getCorreo().equals(a.getCorreo())) {
						return true;
					}
				}
				return false;
			}
		 
		 public Long login(Sesion s) {
				List<Administrador> lista = administradorDao.findAll();

				for (Administrador admins : lista) {
					if (admins.getCorreo().equals(s.getUser()) && admins.getContrasenia().equals(s.getPass())) {
						return admins.getIdadministrador();
					}
				}
				return null;
			}
		 
		
}

