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

import com.gco.proyect.dao.AdministradorDAO;
import com.gco.proyect.dao.EstadosolicitudDAO;
import com.gco.proyect.dao.MultaDAO;
import com.gco.proyect.dao.SolicitudDAO;
import com.gco.proyect.model.Administrador;
import com.gco.proyect.model.Sesion;
import com.gco.proyect.model.Solicitud;



@Controller
@RequestMapping
public class AdministradorCtlr {

	
	@Autowired
	private AdministradorDAO administradorDao;
	@Autowired
	private SolicitudDAO solicitudDao;
	@Autowired
	private EstadosolicitudDAO estadoDao;
	@Autowired
	private MultaDAO multaDao;
	
	
	
	
	
	
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
					Long idadmin=administradorDao.findById(this.login(sesion)).get().getIdadministrador();
					model.addAttribute("nombre",administradorDao.findById(this.login(sesion)).get().getNombre());
					model.addAttribute("idadmin",idadmin);
					return "indexAdmin";
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
		 
		 
		 public List<Solicitud> SoliPend(){
				List<Solicitud> solis = solicitudDao.findAll();
				List<Solicitud> solis2 = new ArrayList<Solicitud>();
				long i=1;
				for (Solicitud soli : solis) {
					if (soli.getIdestado().getIdestadosolicitud()==i) {
						solis2.add(soli);
					}
				}
				
				return solis2;
					
				}
		 
		 public List<Solicitud> SoliApro(){
				List<Solicitud> solis = solicitudDao.findAll();
				List<Solicitud> solis2 = new ArrayList<Solicitud>();
				long i=2;
				for (Solicitud soli : solis) {
					if (soli.getIdestado().getIdestadosolicitud()==i) {
						solis2.add(soli);
					}
				}
			
				return solis2;
					
				}
		 
		 public List<Solicitud> historial(){
				List<Solicitud> solis = solicitudDao.findAll();
				List<Solicitud> solis2 = new ArrayList<Solicitud>();
				long i=3;
			
				for (Solicitud soli : solis) {
					if (soli.getIdestado().getIdestadosolicitud()==i) {
						solis2.add(soli);
					}
				}
			
				return solis2;
					
				}
		 
		 @GetMapping("/admin/citasPendientes/{idadmin}")
		    public String citasPorPaciente(@Validated @ModelAttribute Sesion sesion, BindingResult result, Model model,
					RedirectAttributes attribute,@PathVariable("idadmin") Long idadmin) {
		    	
		    	List<Solicitud> solis = this.SoliPend();
		    
		        model.addAttribute("solis", solis);
		        model.addAttribute("nombre",administradorDao.findById(idadmin).get().getNombre());
				model.addAttribute("idadmin", idadmin);
				model.addAttribute("cod", "/");
		     return "Vistas/citasPendientes";
		    }
		 
		 @GetMapping("/admin/aceptarCita/{idsolicitud}/{idadmin}")
			public String aceptarCita(@Validated @ModelAttribute Sesion sesion, BindingResult result, Model model,
					RedirectAttributes attribute,@PathVariable("idsolicitud") Long idsolicitud,@PathVariable("idadmin") Long idadmin) {
				
			   
			    Solicitud soli= solicitudDao.getById(idsolicitud);
				soli.setIdestado(estadoDao.getById((long) 2));
				solicitudDao.save(soli);
				return "redirect:/admin/citasPendientes/"+idadmin;
			}
		 
		 @GetMapping("/admin/finalizarCita/{idsolicitud}/{idadmin}")
			public String realizarCita(@Validated @ModelAttribute Sesion sesion, BindingResult result, Model model,
					RedirectAttributes attribute,@PathVariable("idsolicitud") Long idsolicitud,@PathVariable("idadmin") Long idadmin) {
				
			   
			    Solicitud soli= solicitudDao.getById(idsolicitud);
				soli.setIdestado(estadoDao.getById((long) 3));
				solicitudDao.save(soli);
				 model.addAttribute("cod", "/");
				return "redirect:/admin/citasAprobadas/"+idadmin;
			}
		 
		 
		 
		 
		 @GetMapping("/admin/rechazarCita/{idsolicitud}/{idadmin}")
			public String rechazarCita(Model model,
					RedirectAttributes attribute,@PathVariable("idsolicitud") Long idsolicitud,@PathVariable("idadmin") Long idadmin) {
			    Solicitud soli= solicitudDao.getById(idsolicitud);
			    soli.setIdestado(estadoDao.getById((long) 4));
				solicitudDao.save(soli);
				return "redirect:/admin/citasPendientes/"+idadmin;
		 }
		 
		 @GetMapping("/admin/cancelarCita/{idsolicitud}/{idadmin}")
			public String cancelarCita(Model model,
					RedirectAttributes attribute,@PathVariable("idsolicitud") Long idsolicitud,@PathVariable("idadmin") Long idadmin) {
			    Solicitud soli= solicitudDao.getById(idsolicitud);
			    soli.setIdestado(estadoDao.getById((long) 4));
				solicitudDao.save(soli);
				return "redirect:/admin/citasAprobadas/"+idadmin;
		 }
		 
		 @GetMapping("/admin/multarCita/{idsolicitud}/{idadmin}")
			public String multarCita(Model model,
					RedirectAttributes attribute,@PathVariable("idsolicitud") Long idsolicitud,@PathVariable("idadmin") Long idadmin) {
			    Solicitud soli= solicitudDao.getById(idsolicitud);
			    soli.setIdmulta(multaDao.getById((long) 2));
				solicitudDao.save(soli);
				return "redirect:/admin/citasAprobadas/"+idadmin;
		 }
		 
		 
		 
		 @GetMapping("/admin/citasAprobadas/{idadmin}")
		 public String citasAprobadas(Model model,
					RedirectAttributes attribute,@PathVariable("idadmin") Long idadmin) {
			 
			 List<Solicitud> solis = this.SoliApro();
			 model.addAttribute("idadmin", idadmin);
			 model.addAttribute("solis", solis);
			 model.addAttribute("nombre", administradorDao.getById(idadmin).getNombre());
			 model.addAttribute("cod", "/");
			 return "Vistas/citasAprobadas";
			 
		 }
		 
		 @GetMapping("/admin/historial/{idadmin}")
		 public String historial(Model model,
					RedirectAttributes attribute,@PathVariable("idadmin") Long idadmin) {
			 
			 List<Solicitud> solis = this.historial();
			 model.addAttribute("idadmin", idadmin);
			 model.addAttribute("solis", solis);
			 model.addAttribute("nombre", administradorDao.getById(idadmin).getNombre());
			 model.addAttribute("cod", "/");
			 return "Vistas/historial";
			 
		 }
		 
		 @GetMapping("/admin/eliminarCita/{idsolicitud}/{idadmin}")
		 public String eliminarCita(Model model,
					RedirectAttributes attribute,@PathVariable("idsolicitud") Long idsolicitud,@PathVariable("idadmin") Long idadmin) {
			 solicitudDao.deleteById(idsolicitud);
			
			 return "redirect:/admin/historial/"+idadmin;
			 
		 }
		
}

