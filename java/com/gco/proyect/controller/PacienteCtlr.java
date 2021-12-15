package com.gco.proyect.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gco.proyect.dao.PacienteDAO;
import com.gco.proyect.dao.SolicitudDAO;

import com.gco.proyect.model.Paciente;
import com.gco.proyect.model.Sesion;
import com.gco.proyect.model.Solicitud;

@Controller
@RequestMapping()
public class PacienteCtlr {

	@Autowired
	private PacienteDAO pacienteDAO;

	@Autowired
	private SolicitudDAO solicitudDao;

	@PostMapping("/ingresoPaciente")
	public String ingresoPaciente(@Validated @ModelAttribute Sesion sesion, BindingResult result, Model model,
			RedirectAttributes attribute) {
		if (this.login(sesion) != null) {
			
			Long idpaciente = this.login(sesion);

			model.addAttribute("idpac", idpaciente);
			model.addAttribute("nombre", pacienteDAO.findById(idpaciente).get().getNombre());
			
			return "index";
		} else
			attribute.addFlashAttribute("error", "Usuario o contraseña no válidos");
		return "redirect:/loginPaciente";
	}

	@GetMapping("/paciente/solicitud/{idpaciente}")
	public String solicitarCita(Model model, @PathVariable("idpaciente") Long idpaciente) {
		Solicitud soli = new Solicitud();
		String t = this.minFecha(LocalDateTime.now().plusDays(1));
		

		model.addAttribute("min", t);
		model.addAttribute("solicitud", soli);
		model.addAttribute("idpac", idpaciente);
		model.addAttribute("nombre", pacienteDAO.findById(idpaciente).get().getNombre());
		return "Vistas/solicitud";
	}

	@GetMapping("/paciente/registro")
	public String registarPaciente(Model model) {
		Paciente paciente = new Paciente();
		model.addAttribute("paciente", paciente);
		return "Vistas/registrarPaciente";
	}
	
	@GetMapping("/paciente/editar/{idpaciente}")
	public String editarPaciente(Model model, @PathVariable("idpaciente") Long idpaciente) {
		Paciente paciente = pacienteDAO.getById(idpaciente);
		 model.addAttribute("nombre", pacienteDAO.findById(idpaciente).get().getNombre());
		model.addAttribute("paciente", paciente);
		model.addAttribute("idpaciente", idpaciente);
		
		return "Vistas/editpaciente";
	}

	@PostMapping("/paciente/guardar")
	public String guardarAdmin(@Validated @ModelAttribute Paciente paciente, BindingResult result, Model model,
			RedirectAttributes attribute) {
		if (this.existe(paciente)) {
			attribute.addFlashAttribute("error", "Ya existe un usuario con ese número de Docuemnto");
			return "redirect:/paciente/registro";
		}else {
			if (pacienteDAO.save(paciente) != null) {
				attribute.addFlashAttribute("success", "Registro Exitoso");
				return "redirect:/loginPaciente";
			}
		}
		
		attribute.addFlashAttribute("error", "Por favor verifique los datos");
		return "redirect:/paciente/registro";
	}
	
	@PostMapping("/paciente/editarPaciente/{idpaciente}")
	public String editarDatosPaciente(@Validated @ModelAttribute Paciente paciente, BindingResult result, Model model,
			RedirectAttributes attribute,@PathVariable("idpaciente") Long idpaciente) {
	        paciente.setIdpaciente(idpaciente);
	        paciente.setContrasenia(pacienteDAO.findById(idpaciente).get().getContrasenia());
	        model.addAttribute("nombre", pacienteDAO.findById(idpaciente).get().getNombre());
			if (pacienteDAO.save(paciente) != null) {
				attribute.addFlashAttribute("success", "Datos editados correctamente");
				return "redirect:/paciente/editar/"+ paciente.getIdpaciente();
			}
		
		attribute.addFlashAttribute("error", "Por favor verifique los datos");
		return "redirect:/paciente/editar/"+ paciente.getIdpaciente();
	}

	public boolean existe(Paciente p) {
		List<Paciente> lista = pacienteDAO.findAll();

		for (Paciente pacientes : lista) {
			if (pacientes.getDocumento().equals(p.getDocumento())&&pacientes.getTipodocumento().equals(p.getTipodocumento())) {
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

	public String minFecha(LocalDateTime fecha) {
		List<Solicitud> solis = solicitudDao.findAll();
		int nCitas = 0;
		for (Solicitud soli : solis) {
			if (soli.getFecha().equals(fecha.format(DateTimeFormatter.ISO_DATE))) {
				nCitas++;
			}
		}
		if (nCitas == 10) {
			return this.minFecha(fecha.plusDays(1));
		} else {
			return fecha.format(DateTimeFormatter.ISO_DATE);
		}
	}

	public List<Solicitud> SoliPorPac(Long idpaciente) {
		List<Solicitud> solis = solicitudDao.findAll();
		List<Solicitud> solis2 = new ArrayList<Solicitud>();
		for (Solicitud soli : solis) {
			if (soli.getIdpaciente().getIdpaciente().equals(idpaciente)) {
				solis2.add(soli);
			}
		}
		System.out.println(solis2);
		return solis2;

	}

	@GetMapping("/verCitas/{idpaciente}")
	public String citasPorPaciente(@Validated @ModelAttribute Solicitud solicitud, BindingResult result, Model model,
			RedirectAttributes attribute, @PathVariable("idpaciente") Long idpaciente) {

		List<Solicitud> solis = this.SoliPorPac(idpaciente);

		model.addAttribute("solis", solis);
		model.addAttribute("nombre", solicitud.getIdpaciente().getNombre());
		model.addAttribute("idpac", idpaciente);
		return "Vistas/citasPaciente";
	}

	@GetMapping("/cancelarCitas/{idsolicitud}")
	public String cancelarSolicitud(RedirectAttributes attribute, @PathVariable("idsolicitud") Long idsolicitud) {
		
		
		Long id = solicitudDao.findById(idsolicitud).get().getIdpaciente().getIdpaciente();
		if(solicitudDao.findById(idsolicitud).get().getIdmulta().getMonto()==0) {
			solicitudDao.deleteById(idsolicitud);
			return "redirect:/verCitas/"+id;
		}
		
		
		return "redirect:/verCitas/"+id;

	}
	
	
	
	@GetMapping("/verPendientes/{idpaciente}")
	public String citasPendientes(@Validated @ModelAttribute Solicitud solicitud, BindingResult result, Model model,
			RedirectAttributes attribute, @PathVariable("idpaciente") Long idpaciente) {
        Long i =(long) 1;
		List<Solicitud> solis = this.SoliPorEst(i, idpaciente);
        
		model.addAttribute("solis", solis);
		model.addAttribute("nombre", solicitud.getIdpaciente().getNombre());
		model.addAttribute("idpac", idpaciente);
		return "Vistas/citasPaciente";
	}
	@GetMapping("/verAprobadas/{idpaciente}")
	public String citasAprobadas(@Validated @ModelAttribute Solicitud solicitud, BindingResult result, Model model,
			RedirectAttributes attribute, @PathVariable("idpaciente") Long idpaciente) {
        Long i =(long) 2;
		List<Solicitud> solis = this.SoliPorEst(i,idpaciente);

		model.addAttribute("solis", solis);
		model.addAttribute("nombre", solicitud.getIdpaciente().getNombre());
		model.addAttribute("idpac", idpaciente);
		return "Vistas/citasPaciente";
	}
	
	@GetMapping("/verRechazadas/{idpaciente}")
	public String citasRechazadas(@Validated @ModelAttribute Solicitud solicitud, BindingResult result, Model model,
			RedirectAttributes attribute, @PathVariable("idpaciente") Long idpaciente) {
        Long i =(long) 4;
		List<Solicitud> solis = this.SoliPorEst(i,idpaciente);

		model.addAttribute("solis", solis);
		model.addAttribute("nombre", solicitud.getIdpaciente().getNombre());
		model.addAttribute("idpac", idpaciente);
		return "Vistas/citasPaciente";
	}
	
	@GetMapping("/verRealizadas/{idpaciente}")
	public String citasRealizadas(@Validated @ModelAttribute Solicitud solicitud, BindingResult result, Model model,
			RedirectAttributes attribute, @PathVariable("idpaciente") Long idpaciente) {
        Long i =(long) 3;
		List<Solicitud> solis = this.SoliPorEst(i,idpaciente);

		model.addAttribute("solis", solis);
		model.addAttribute("nombre", solicitud.getIdpaciente().getNombre());
		model.addAttribute("idpac", idpaciente);
		return "Vistas/citasPaciente";
	}
	
	@GetMapping("/verMultas/{idpaciente}")
	public String citasMulta(@Validated @ModelAttribute Solicitud solicitud, BindingResult result, Model model,
			RedirectAttributes attribute, @PathVariable("idpaciente") Long idpaciente) {

	
		return "Vistas/citasPaciente";
	}
	
	public List<Solicitud> SoliPorEst(Long idest, Long idpaciente) {
		List<Solicitud> solis = solicitudDao.findAll();
		List<Solicitud> solis2 = new ArrayList<Solicitud>();
		for (Solicitud soli : solis) {
			if (soli.getIdestado().getIdestadosolicitud().equals(idest)&& soli.getIdpaciente().getIdpaciente().equals(idpaciente)) {
				solis2.add(soli);
			}
		}
	
		return solis2;

	}
	

}
