package com.gco.proyect.controller;


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


import com.gco.proyect.dao.EstadosolicitudDAO;
import com.gco.proyect.dao.HorarioDAO;
import com.gco.proyect.dao.MultaDAO;
import com.gco.proyect.dao.PacienteDAO;
import com.gco.proyect.dao.SolicitudDAO;
import com.gco.proyect.dao.TiposolicitudDAO;

import com.gco.proyect.model.Estadosolicitud;
import com.gco.proyect.model.Horario;
import com.gco.proyect.model.Multa;

import com.gco.proyect.model.Solicitud;
import com.gco.proyect.model.Tiposolicitud;


@Controller
@RequestMapping("solicitud")
public class SolicitudCtlr {
	

	@Autowired
	private SolicitudDAO solicitudDao;
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

	
	@PostMapping("/horario/{idpaciente}")
	public String verHorarios(@Validated @ModelAttribute Solicitud solicitud, BindingResult result, Model model,
			RedirectAttributes attribute, @PathVariable("idpaciente") Long idpaciente) {
		
		List<Horario> horarioDisp = this.horarioDisp(solicitud);
		List<Tiposolicitud> tipoSol = (List<Tiposolicitud>) tipoSolDao.findAll();

		model.addAttribute("horas", horarioDisp);
		model.addAttribute("hoy", solicitud.getFecha());
		model.addAttribute("tiposol", tipoSol);
		model.addAttribute("idpac", idpaciente);
		model.addAttribute("nombre", pacienteDao.findById(idpaciente).get().getNombre());
		if(this.soliExist(idpaciente, solicitud.getFecha())) {
			return "redirect:/paciente/solicitud/"+idpaciente;
		}else {
			return "Vistas/Solicitud2";
		}
	}

	
	
	@PostMapping("/solicitar/{idpaciente}")
	public String solicitar(@Validated @ModelAttribute Solicitud solicitud, BindingResult result, Model model,
			RedirectAttributes attribute, @PathVariable("idpaciente") Long idpaciente) {
		if(this.multaPend(idpaciente).getMonto()==0) {
			solicitud.setIdmulta(multaDao.getById((long) 1));
		}else {
			solicitud.setIdmulta(this.multaPend(idpaciente));
		}
		
		solicitud.setIdestado(estadoDao.getById((long) 1));
		solicitud.setIdpaciente(pacienteDao.getById(idpaciente));
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
				
			}
		}
		return horas1;
	}
	
	public Multa multaPend(Long idpac) {
	Long i=(long) 1;
	Multa m=new Multa();
	List<Solicitud> solis = this.SoliPorPac(idpac);
	
	for (Solicitud soli : solis) {
		if (soli.getIdmulta().getIdmulta()!=i) {
			m= multaDao.getById(soli.getIdmulta().getIdmulta());
		}
	}


	return m;
	}
	
	
	@GetMapping("/editarEstado/{idsolicitud}")
	public String editarSolicitud(Model model, @PathVariable("idsolicitud") Long idsolicitud) {

		Solicitud solic = solicitudDao.getById(idsolicitud);
		List<Estadosolicitud> listEstados = (List<Estadosolicitud>) estadoDao.findAll();

		model.addAttribute("estados", listEstados);
		model.addAttribute("solic", solic);

		return "/porHacer";
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
	
	public Boolean soliExist(Long idpac, String fecha) {
		
		List<Solicitud> solis = this.SoliPorPac(idpac);
		
		for (Solicitud soli : solis) {
			if (soli.getFecha().equals(fecha)) {
				return true;
			}
		}
		return false;
		}
	
	
	
	
	}
