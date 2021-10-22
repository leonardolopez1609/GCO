package com.gco.proyect.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="solicitud")
public class Solicitud {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idsolicitud;

	private Date fecha;
	private String observaciones;
	
	@ManyToOne
	@JoinColumn(name="id_tiposolicitud")
	private Tiposolicitud tiposolicitud;
	
	@ManyToOne
	@JoinColumn(name="id_estadosolicitud")
	private Tiposolicitud idestado;
	
	@ManyToOne
	@JoinColumn(name="id_multa")
	private Tiposolicitud idmulta;
	
	@ManyToOne
	@JoinColumn(name="id_paciente")
	private Tiposolicitud idpaciente;
	
	@ManyToOne
	@JoinColumn(name="id_administrador")
	private Tiposolicitud idadministrador;

	public Long getIdsolicitud() {
		return idsolicitud;
	}

	public void setIdsolicitud(Long idsolicitud) {
		this.idsolicitud = idsolicitud;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Tiposolicitud getTiposolicitud() {
		return tiposolicitud;
	}

	public void setTiposolicitud(Tiposolicitud tiposolicitud) {
		this.tiposolicitud = tiposolicitud;
	}

	public Tiposolicitud getIdestado() {
		return idestado;
	}

	public void setIdestado(Tiposolicitud idestado) {
		this.idestado = idestado;
	}

	public Tiposolicitud getIdmulta() {
		return idmulta;
	}

	public void setIdmulta(Tiposolicitud idmulta) {
		this.idmulta = idmulta;
	}

	public Tiposolicitud getIdpaciente() {
		return idpaciente;
	}

	public void setIdpaciente(Tiposolicitud idpaciente) {
		this.idpaciente = idpaciente;
	}

	public Tiposolicitud getIdadministrador() {
		return idadministrador;
	}

	public void setIdadministrador(Tiposolicitud idadministrador) {
		this.idadministrador = idadministrador;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Solicitud(Long idsolicitud, Date fecha, String observaciones, Tiposolicitud tiposolicitud,
			Tiposolicitud idestado, Tiposolicitud idmulta, Tiposolicitud idpaciente, Tiposolicitud idadministrador) {
		super();
		this.idsolicitud = idsolicitud;
		this.fecha = fecha;
		this.observaciones = observaciones;
		this.tiposolicitud = tiposolicitud;
		this.idestado = idestado;
		this.idmulta = idmulta;
		this.idpaciente = idpaciente;
		this.idadministrador = idadministrador;
	}

	public Solicitud() {
		super();
	}

	@Override
	public String toString() {
		return "Solicitud [idsolicitud=" + idsolicitud + ", fecha=" + fecha + ", observaciones=" + observaciones
				+ ", tiposolicitud=" + tiposolicitud + ", idestado=" + idestado + ", idmulta=" + idmulta
				+ ", idpaciente=" + idpaciente + ", idadministrador=" + idadministrador + "]";
	}
	
	
	
}
