package com.gco.proyect.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tiposolicitud")
public class Tiposolicitud {
	

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idtiposolicitud;

	private String nombre;
	private String descripcion;
	public Long getIdtiposolicitud() {
		return idtiposolicitud;
	}
	public void setIdtiposolicitud(Long idtiposolicitud) {
		this.idtiposolicitud = idtiposolicitud;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripción() {
		return descripcion;
	}
	public void setDescripción(String descripción) {
		this.descripcion = descripción;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Tiposolicitud(Long idtiposolicitud, String nombre, String descripción) {
		super();
		this.idtiposolicitud = idtiposolicitud;
		this.nombre = nombre;
		this.descripcion = descripción;
	}
	public Tiposolicitud() {
		super();
	}
	@Override
	public String toString() {
		return "Tiposolicitud [idtiposolicitud=" + idtiposolicitud + ", nombre=" + nombre + ", descripción="
				+ descripcion + "]";
	}
	
	
	
	
	
	
	
	
}
