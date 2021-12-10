package com.gco.proyect.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.gco.proyect.dao.AdministradorDAO;
import com.gco.proyect.dao.EstadosolicitudDAO;
import com.gco.proyect.dao.HorarioDAO;
import com.gco.proyect.dao.MultaDAO;
import com.gco.proyect.dao.PacienteDAO;
import com.gco.proyect.dao.SolicitudDAO;
import com.gco.proyect.dao.TiposolicitudDAO;
import com.gco.proyect.model.Administrador;
import com.gco.proyect.model.Estadosolicitud;
import com.gco.proyect.model.Horario;
import com.gco.proyect.model.Multa;
import com.gco.proyect.model.Sesion;
import com.gco.proyect.model.Solicitud;
import com.gco.proyect.model.Tiposolicitud;


@Controller
@RequestMapping("solicitud")
public class SolicitudCtlr {

	@Autowired
	private SolicitudDAO solicitudDao;
	@Autowired
	private AdministradorDAO adminDao;
	@Autowired
	private HorarioDAO horarioDao;
	@Autowired
	private TiposolicitudDAO tipoSolDao;

	@Autowired
	private MultaDAO multaDao;
	@Autowired
	private EstadosolicitudDAO estadoDao;
	@Autowired
	private PacienteDAO pacienteDao;

	
	
	@PostMapping("/fecha/{idpaciente}")
	public String verFechas(@Validated @ModelAttribute Solicitud solicitud, BindingResult result, Model model,
			RedirectAttributes attribute, @PathVariable("idpaciente") Long idpaciente) {
		System.out.println(solicitud);
		//String t = this.minFecha(LocalDateTime.now().plusDays(1));
		//Long idAdmin = solicitud.getIdadministrador().getIdadministrador();
		//String administrador = solicitud.getIdadministrador().getNombre();

		model.addAttribute("min", solicitud.getFecha());
		//model.addAttribute("administrador", administrador);
		//model.addAttribute("idAdmin", idAdmin);
		model.addAttribute("idpac", idpaciente);
		model.addAttribute("nombre", pacienteDao.findById(idpaciente).get().getNombre());

		return "Vistas/Solicitud1";
	}

	
	
	@PostMapping("/horario/{idpaciente}")
	public String verHorarios(@Validated @ModelAttribute Solicitud solicitud, BindingResult result, Model model,
			RedirectAttributes attribute, @PathVariable("idpaciente") Long idpaciente) {
		System.out.println(solicitud);
		//String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		List<Horario> horarioDisp = this.horarioDisp(solicitud);
		//List<Administrador> admins = (List<Administrador>) adminDao.findAll();
		//Long idAdmin = solicitud.getIdadministrador().getIdadministrador();
		//String administrador = solicitud.getIdadministrador().getNombre();
		List<Tiposolicitud> tipoSol = (List<Tiposolicitud>) tipoSolDao.findAll();

		model.addAttribute("horas", horarioDisp);
		//model.addAttribute("min", timeStamp);
		model.addAttribute("hoy", solicitud.getFecha());
		//model.addAttribute("administrador", administrador);
		//model.addAttribute("idAdmin", idAdmin);
		model.addAttribute("tiposol", tipoSol);
		model.addAttribute("idpac", idpaciente);
		model.addAttribute("nombre", pacienteDao.findById(idpaciente).get().getNombre());

		return "Vistas/Solicitud2";
	}

	
	
	@PostMapping("/solicitar/{idpaciente}")
	public String solicitar(@Validated @ModelAttribute Solicitud solicitud, BindingResult result, Model model,
			RedirectAttributes attribute, @PathVariable("idpaciente") Long idpaciente) {
		solicitud.setIdmulta(multaDao.getById((long) 1));
		solicitud.setIdestado(estadoDao.getById((long) 1));
		solicitud.setIdpaciente(pacienteDao.getById(idpaciente));
		System.out.println(solicitud);
		solicitudDao.save(solicitud);
		model.addAttribute("usuario", solicitud.getIdpaciente().getNombre());
		model.addAttribute("idpac", idpaciente);
		return "index";
	}

	
	
	public List<Horario> horarioDisp(Solicitud solicitud) {
		List<Horario> horas1 = horarioDao.findAll();
		List<Solicitud> solis = solicitudDao.findAll();
		for (Solicitud soli : solis) {
			if (soli.getFecha().equals(solicitud.getFecha())) {
				horas1.remove(soli.getIdhorario());
				System.out.println(soli.getIdhorario());
			}
		}
		return horas1;
	}
	
	
	@GetMapping("/editarEstado/{idsolicitud}")
	public String editarSolicitud(Model model, @PathVariable("idsolicitud") Long idsolicitud) {

		Solicitud solic = solicitudDao.getById(idsolicitud);
		List<Estadosolicitud> listEstados = (List<Estadosolicitud>) estadoDao.findAll();

		model.addAttribute("estados", listEstados);
		model.addAttribute("solic", solic);

		return "/porHacer";
	}

	
	
	
	
	
	}
