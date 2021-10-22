package com.gco.proyect.REST;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gco.proyect.dao.AdministradorDAO;
import com.gco.proyect.model.Administrador;




@RestController
@RequestMapping("admin")
public class AdministradorRest {

	@Autowired
	AdministradorDAO administradorDao;
	
	@GetMapping("/list")
	public List<Administrador> getAdministrador() {
		return administradorDao.findAll();
	}
	

	@PostMapping
	public Administrador saveAdministrador(@RequestBody Administrador administrador) {
		return administradorDao.save(administrador);
	}

	@PutMapping
	public Administrador editAdministrador(@RequestBody Administrador administrador) {
		if(administradorDao.existsById(administrador.getIdadministrador())) {
			return administradorDao.save(administrador);
		}else {
		return null;
	}
	}
	
	@GetMapping("/delete/{idadministrador}")
	public Boolean eliminarPlan(@Validated @ModelAttribute Administrador administrador, BindingResult result,
			Model model, RedirectAttributes attribute,@PathVariable("idadministrador") Long idadministrador) {
		if(administradorDao.existsById(administrador.getIdadministrador())) {
			administradorDao.deleteById(idadministrador);
			 return true;
		}else {
		return false;
	}
		
	}
	
	
	
	
	
}
