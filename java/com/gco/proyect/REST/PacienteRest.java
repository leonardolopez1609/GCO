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
import com.gco.proyect.dao.PacienteDAO;
import com.gco.proyect.model.Paciente;

@RestController
@RequestMapping("patient")
public class PacienteRest {

	
	@Autowired
	PacienteDAO pacienteDao;
	
	@GetMapping("/list")
	public List<Paciente> getPaciente() {
		return pacienteDao.findAll();
	}
	

	@PostMapping
	public Paciente savePaciente(@RequestBody Paciente paciente) {
		return pacienteDao.save(paciente);
	}

	@PutMapping
	public Paciente editPaciente(@RequestBody Paciente paciente) {
		if(pacienteDao.existsById(paciente.getIdpaciente())) {
			return pacienteDao.save(paciente);
		}else {
		return null;
	}
	}
	
	@GetMapping("/delete/{idpaciente}")
	public Boolean eliminarPlan(@Validated @ModelAttribute Paciente paciente, BindingResult result,
			Model model, RedirectAttributes attribute,@PathVariable("idpaciente") Long idpaciente) {
		if(pacienteDao.existsById(paciente.getIdpaciente())) {
			pacienteDao.deleteById(idpaciente);
			 return true;
		}else {
		return false;
	}
		
	}
	
}
