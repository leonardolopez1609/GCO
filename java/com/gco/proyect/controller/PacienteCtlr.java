package com.gco.proyect.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
import com.gco.proyect.dao.PacienteDAO;
import com.gco.proyect.dao.SolicitudDAO;
import com.gco.proyect.model.Administrador;
import com.gco.proyect.model.Paciente;
import com.gco.proyect.model.Sesion;
import com.gco.proyect.model.Solicitud;

@Controller
//@RestController
@RequestMapping()
public class PacienteCtlr {

	@Autowired
	private PacienteDAO pacienteDAO;
	@Autowired
	private AdministradorDAO adminDao;
	@Autowired
	private SolicitudDAO solicitudDao;

	@PostMapping("/ingresoPaciente")
	public String ingresoPaciente(@Validated @ModelAttribute Sesion sesion, BindingResult result, Model model,
			RedirectAttributes attribute) {
		if (this.login(sesion) != null) {
			Solicitud soli = new Solicitud();
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			List<Administrador> admins = (List<Administrador>) adminDao.findAll();
			Long idpaciente = this.login(sesion);

			model.addAttribute("solicitud", soli);
			model.addAttribute("admins", admins);
			model.addAttribute("idpac", idpaciente);
			model.addAttribute("nombre", pacienteDAO.findById(idpaciente).get().getNombre());
			return "Vistas/Solicitud";
		} else
			attribute.addFlashAttribute("error", "Usuario o contraseña no válidos");
		return "redirect:/loginPaciente";
	}

	@GetMapping("/paciente/registro")
	public String registarAdmin(Model model) {
		Paciente paciente = new Paciente();
		model.addAttribute("paciente", paciente);
		return "Vistas/registrarPaciente";
	}

	@PostMapping("/paciente/guardar")
	public String guardarAdmin(@Validated @ModelAttribute Paciente paciente, BindingResult result, Model model,
			RedirectAttributes attribute) {
		if (!this.existe(paciente)) {
			if (pacienteDAO.save(paciente) != null) {
				attribute.addFlashAttribute("success", "Registro Exitoso");
				return "redirect:/loginPaciente";
			}
		}
		attribute.addFlashAttribute("error", "Por favor verifique los datos");
		return "redirect:/paciente/registro";
	}

	public boolean existe(Paciente p) {
		List<Paciente> lista = pacienteDAO.findAll();

		for (Paciente pacientes : lista) {
			if (pacientes.getDocumento().equals(p.getDocumento())) {
				return true;
			}
		}
		return false;
	}

	public Long login(Sesion s) {
		List<Paciente> lista = pacienteDAO.findAll();

		for (Paciente pacientes : lista) {
			if (pacientes.getDocumento().equals(s.getUser()) && pacientes.getContrasenia().equals(s.getPass())) {
				return pacientes.getIdpaciente();
			}
		}
		return null;
	}

	
}
