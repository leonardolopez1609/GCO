package com.gco.proyect.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="estadosolicitud")
public class Estadosolicitud {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idestadosolicitud;	
	
	private String nombre;
	private String descripcion;
	public Long getIdestadosolicitud() {
		return idestadosolicitud;
	}
	public void setIdestadosolicitud(Long idestadosolicitud) {
		this.idestadosolicitud = idestadosolicitud;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Estadosolicitud(Long idestadosolicitud, String nombre, String descripcion) {
		super();
		this.idestadosolicitud = idestadosolicitud;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	public Estadosolicitud() {
		super();
	}
	@Override
	public String toString() {
		return "Estadosolicitud [idestadosolicitud=" + idestadosolicitud + ", nombre=" + nombre + ", descripcion="
				+ descripcion + "]";
	}
	
	
	
}
