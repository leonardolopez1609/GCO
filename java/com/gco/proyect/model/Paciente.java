package com.gco.proyect.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="paciente")
public class Paciente {

	

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idpaciente;
	
	private String tipodocumento;
	private String documento;
	private String nombre;
	private String apellidos;
	private String telefono;
	private String correo;
	private String contrasenia;
	public Long getIdpaciente() {
		return idpaciente;
	}
	public void setIdpaciente(Long idpaciente) {
		this.idpaciente = idpaciente;
	}
	public String getTipodocumento() {
		return tipodocumento;
	}
	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Paciente(Long idpaciente, String tipodocumento, String documento, String nombre, String apellidos,
			String telefono, String correo, String contrasenia) {
		super();
		this.idpaciente = idpaciente;
		this.tipodocumento = tipodocumento;
		this.documento = documento;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.correo = correo;
		this.contrasenia = contrasenia;
	}
	public Paciente() {
		super();
	}
	@Override
	public String toString() {
		return "Paciente [idpaciente=" + idpaciente + ", tipodocumento=" + tipodocumento + ", documento=" + documento
				+ ", nombre=" + nombre + ", apellidos=" + apellidos + ", telefono=" + telefono + ", correo=" + correo
				+ ", contrasenia=" + contrasenia + "]";
	}
	
	
	
	
	}
