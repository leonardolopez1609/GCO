package com.gco.proyect.dao;



import org.springframework.data.jpa.repository.JpaRepository;


import com.gco.proyect.model.Paciente;

public interface PacienteDAO extends JpaRepository<Paciente, Long> {

	
	

}
