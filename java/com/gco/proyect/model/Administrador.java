package com.gco.proyect.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name="administrador")
public class Administrador {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idadministrador;
	
	private String nombre;
	private String correo;
	private String contrasenia;
	public Long getIdadministrador() {
		return idadministrador;
	}
	public void setIdadministrador(Long idadministrador) {
		this.idadministrador = idadministrador;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	
	public Administrador(Long idadministrador, String nombre, String correo, String contrasenia) {
		super();
		this.idadministrador = idadministrador;
		this.nombre = nombre;
		this.correo = correo;
		this.contrasenia = contrasenia;
	}
	
	
	
	
	public Administrador(String nombre, String correo, String contrasenia) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.contrasenia = contrasenia;
	}
	public Administrador() {
		super();
	}
	
	
	
	@Override
	public String toString() {
		return "Administrador [idadministrador=" + idadministrador + ", nombre=" + nombre + ", correo=" + correo
				+ ", contrasenia=" + contrasenia + "]";
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
}
