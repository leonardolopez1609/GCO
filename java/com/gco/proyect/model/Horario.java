package com.gco.proyect.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="horario")
public class Horario {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_horario;
	
	private String hora;

	public Long getId_horario() {
		return id_horario;
	}

	public void setId_horario(Long id_horario) {
		this.id_horario = id_horario;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Horario(Long id_horario, String hora) {
		super();
		this.id_horario = id_horario;
		this.hora = hora;
	}

	public Horario() {
		super();
	}

	@Override
	public String toString() {
		return "Horario [id_horario=" + id_horario + ", hora=" + hora + "]";
	}
	
	
}
