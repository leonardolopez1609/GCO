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

import com.gco.proyect.dao.AdministradorDAO;
import com.gco.proyect.dao.EstadosolicitudDAO;
import com.gco.proyect.dao.HorarioDAO;
import com.gco.proyect.dao.MultaDAO;
import com.gco.proyect.dao.SolicitudDAO;
import com.gco.proyect.model.Administrador;
import com.gco.proyect.model.Horario;
import com.gco.proyect.model.Multa;
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
	@Autowired
	private HorarioDAO horarioDao;
	
	
	
	
	
	
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
			  if(this.existe(administrador)) {
				  attribute.addFlashAttribute("error", "Ya existe un Administrador con ese email");
					return "redirect:/admin/registro"; 
			  }else {
				  
			  if(administradorDao.save(administrador)!=null) {
					model.addAttribute("usuario", administrador.getNombre());
					attribute.addFlashAttribute("success", "Registro Exitoso");
					return "redirect:/loginAdmin";
			  }}
			  
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
		 
		 
		 public List<Solicitud> SoliHoy(List<Solicitud> solis){
				
				List<Solicitud> solis2 = new ArrayList<Solicitud>();
				LocalDateTime hoy =LocalDateTime.now();
				hoy.format(DateTimeFormatter.ISO_LOCAL_DATE);
				String h= hoy.getYear()+"-"+hoy.getMonthValue()+"-"+hoy.getDayOfMonth();
				
				for (Solicitud soli : solis) {
					if (soli.getFecha().equals(h)) {
						solis2.add(soli);
					}
				}
				
				return solis2;
					
				}
		 
		 public List<Solicitud> SoliMult(List<Solicitud> solis){
				
				List<Solicitud> solis2 = new ArrayList<Solicitud>();
				
				for (Solicitud soli : solis) {
					if (soli.getIdmulta().getMonto()>0) {
						solis2.add(soli);
					}
				}
				
				return solis2;
					
				}
		 
		 
		 public List<Solicitud> SoliManana(List<Solicitud> solis){
				
				List<Solicitud> solis2 = new ArrayList<Solicitud>();
				LocalDateTime hoy =LocalDateTime.now().plusDays(1);
				//hoy.format(DateTimeFormatter.ISO_DATE);
				hoy.format(DateTimeFormatter.ISO_LOCAL_DATE);
				String h= hoy.getYear()+"-"+hoy.getMonthValue()+"-"+hoy.getDayOfMonth();
				for (Solicitud soli : solis) {
					if (soli.getFecha().equals(h)) {
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
		 
		 @GetMapping("/admin/citasHoy/{idadmin}")
		    public String citasHoy(@Validated @ModelAttribute Sesion sesion, BindingResult result, Model model,
					RedirectAttributes attribute,@PathVariable("idadmin") Long idadmin) {
			    
			    List<Solicitud> solis2 = this.SoliPend();
		    	List<Solicitud> solis = this.SoliHoy(solis2);
		    
		        model.addAttribute("solis", solis);
		        model.addAttribute("nombre",administradorDao.findById(idadmin).get().getNombre());
				model.addAttribute("idadmin", idadmin);
				model.addAttribute("cod", "/");
		     return "Vistas/citasPendientes";
		    }
		 
		 @GetMapping("/admin/citasHoyA/{idadmin}")
		    public String citasHoyA(@Validated @ModelAttribute Sesion sesion, BindingResult result, Model model,
					RedirectAttributes attribute,@PathVariable("idadmin") Long idadmin) {
			    
			    List<Solicitud> solis2 = this.SoliApro();
		    	List<Solicitud> solis = this.SoliHoy(solis2);
		    
		        model.addAttribute("solis", solis);
		        model.addAttribute("nombre",administradorDao.findById(idadmin).get().getNombre());
				model.addAttribute("idadmin", idadmin);
				model.addAttribute("cod", "/");
		     return "Vistas/citasAprobadas";
		    }
		 
		 @GetMapping("/admin/multar/{idadmin}")
		    public String multar1(@Validated @ModelAttribute Sesion sesion, BindingResult result, Model model,
					RedirectAttributes attribute,@PathVariable("idadmin") Long idadmin) {
			    
			    List<Solicitud> solis2 = this.SoliApro();
		    	List<Solicitud> solis = this.SoliHoy(solis2);
		    
		        model.addAttribute("solis", solis);
		        model.addAttribute("nombre",administradorDao.findById(idadmin).get().getNombre());
				model.addAttribute("idadmin", idadmin);
				model.addAttribute("cod", "/");
		     return "Vistas/multar1";
		    }
		 
		 @GetMapping("/admin/desmultar/{idadmin}")
		    public String desmultar(@Validated @ModelAttribute Sesion sesion, BindingResult result, Model model,
					RedirectAttributes attribute,@PathVariable("idadmin") Long idadmin) {
			    
			    List<Solicitud> solis2 = this.SoliApro();
		    	List<Solicitud> solis = this.SoliMult(solis2);
		    
		        model.addAttribute("solis", solis);
		        model.addAttribute("nombre",administradorDao.findById(idadmin).get().getNombre());
				model.addAttribute("idadmin", idadmin);
				model.addAttribute("cod", "/");
		     return "Vistas/desmultar";
		    }
		 
		 
		 @GetMapping("/admin/desmultar2/{idadmin}/{idsolicitud}")
		    public String desmultar2(@Validated @ModelAttribute Sesion sesion, BindingResult result, Model model,
					RedirectAttributes attribute,@PathVariable("idadmin") Long idadmin,@PathVariable("idsolicitud") Long idsolicitud) {
			    
			    Solicitud s=solicitudDao.findById(idsolicitud).get();
			    s.getIdmulta().setMonto(0);
			    solicitudDao.save(s);
			
		     return "redirect:/admin/desmultar/"+idadmin;
		    }
		 
		 @GetMapping("/admin/multar2/{idadmin}/{idsolicitud}")
		    public String multar2(Model model,
					RedirectAttributes attribute,@PathVariable("idadmin") Long idadmin,@PathVariable("idsolicitud") Long idsolicitud) {
			 String t = this.minFecha(LocalDateTime.now().plusDays(1));
			 Solicitud solicitud=new Solicitud();
			 
			    model.addAttribute("min", t);
			    model.addAttribute("solicitud", solicitud);
				model.addAttribute("idsolicitud", idsolicitud);
				model.addAttribute("nombre",administradorDao.findById(idadmin).get().getNombre());
				model.addAttribute("cod", "/");
		     return "Vistas/multa2";
		    }
		 
		 @PostMapping("/admin/multar3/{idsolicitud}/{idadmin}")
		    public String multar3(@Validated @ModelAttribute Solicitud solicitud, BindingResult result,Model model,
					RedirectAttributes attribute,@PathVariable("idadmin") Long idadmin,@PathVariable("idsolicitud") Long idsolicitud) {
			 String t=solicitud.getFecha();
			 List<Horario> horarioDisp = this.horarioDisp(solicitud);
			
			    model.addAttribute("horas", horarioDisp);
			    model.addAttribute("hoy", t);
			    model.addAttribute("solicitud", solicitud);
				model.addAttribute("idsolicitud", idsolicitud);
				model.addAttribute("nombre",administradorDao.findById(idadmin).get().getNombre());
				model.addAttribute("cod", "/");
		     return "Vistas/multa3";
		    }
		 
		 @PostMapping("/admin/multar4/{idsolicitud}/{idadmin}")
		    public String multar4(@Validated @ModelAttribute Solicitud solicitud, BindingResult result,Model model,
					RedirectAttributes attribute,@PathVariable("idadmin") Long idadmin,@PathVariable("idsolicitud") Long idsolicitud) {
			
			 Solicitud soli = solicitudDao.findById(idsolicitud).get();
			 soli.setFecha(solicitud.getFecha());
			 soli.setIdhorario(solicitud.getIdhorario());
			 solicitudDao.save(soli);
   
			     Multa m= new Multa();
			
			    model.addAttribute("multa", m);
				model.addAttribute("idsolicitud", idsolicitud);
				model.addAttribute("idadmin", idadmin);
				model.addAttribute("nombre",administradorDao.findById(idadmin).get().getNombre());
				model.addAttribute("cod", "/");
		     return "Vistas/multa4";
		    }
		 
		 @PostMapping("/admin/multar5/{idsolicitud}/{idadmin}")
		    public String multar5(@Validated @ModelAttribute Multa multa, BindingResult result,Model model,
					RedirectAttributes attribute,@PathVariable("idadmin") Long idadmin,@PathVariable("idsolicitud") Long idsolicitud) {
			 
			 multaDao.save(multa);
			 Solicitud soli = solicitudDao.findById(idsolicitud).get();
			 soli.setIdmulta(multa);
			 solicitudDao.save(soli);

		     return "redirect:/admin/citasAprobadas/"+idadmin;
		    }
		 
		 
		 
		 
		 
		 @GetMapping("/admin/citasManana/{idadmin}")
		    public String citasManana(@Validated @ModelAttribute Sesion sesion, BindingResult result, Model model,
					RedirectAttributes attribute,@PathVariable("idadmin") Long idadmin) {
			    
			    List<Solicitud> solis2 = this.SoliPend();
		    	List<Solicitud> solis = this.SoliManana(solis2);
		    
		        model.addAttribute("solis", solis);
		        model.addAttribute("nombre",administradorDao.findById(idadmin).get().getNombre());
				model.addAttribute("idadmin", idadmin);
				model.addAttribute("cod", "/");
		     return "Vistas/citasPendientes";
		    }
		 @GetMapping("/admin/citasMananaA/{idadmin}")
		    public String citasMananaA(@Validated @ModelAttribute Sesion sesion, BindingResult result, Model model,
					RedirectAttributes attribute,@PathVariable("idadmin") Long idadmin) {
			    
			    List<Solicitud> solis2 = this.SoliApro();
		    	List<Solicitud> solis = this.SoliManana(solis2);
		    
		        model.addAttribute("solis", solis);
		        model.addAttribute("nombre",administradorDao.findById(idadmin).get().getNombre());
				model.addAttribute("idadmin", idadmin);
				model.addAttribute("cod", "/");
		     return "Vistas/citasAprobadas";
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

