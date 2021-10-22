package com.gco.proyect.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="multa")
public class Multa {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idmulta;	
	
	private long monto;
	private String descripcion;
	public Long getIdmulta() {
		return idmulta;
	}
	public void setIdmulta(Long idmulta) {
		this.idmulta = idmulta;
	}
	public long getMonto() {
		return monto;
	}
	public void setMonto(long monto) {
		this.monto = monto;
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
	public Multa(Long idmulta, long monto, String descripcion) {
		super();
		this.idmulta = idmulta;
		this.monto = monto;
		this.descripcion = descripcion;
	}
	public Multa() {
		super();
	}
	@Override
	public String toString() {
		return "Multa [idmulta=" + idmulta + ", monto=" + monto + ", descripcion=" + descripcion + "]";
	}
	
	
	
}
