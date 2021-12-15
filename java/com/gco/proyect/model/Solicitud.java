package com.gco.proyect.model;



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

	private String fecha;
	private String observaciones;
	
	@ManyToOne
	@JoinColumn(name="id_tiposolicitud")
	private Tiposolicitud tiposolicitud;
	
	@ManyToOne
	@JoinColumn(name="id_estadosolicitud")
	private Estadosolicitud idestado;
	
	@ManyToOne
	@JoinColumn(name="id_multa")
	private Multa idmulta;
	
	@ManyToOne
	@JoinColumn(name="id_paciente")
	private Paciente idpaciente;
	
	
	@ManyToOne
	@JoinColumn(name="id_horario")
	private Horario idhorario;

	public Long getIdsolicitud() {
		return idsolicitud;
	}

	public void setIdsolicitud(Long idsolicitud) {
		this.idsolicitud = idsolicitud;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
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

	public Estadosolicitud getIdestado() {
		return idestado;
	}

	public void setIdestado(Estadosolicitud idestado) {
		this.idestado = idestado;
	}

	public Multa getIdmulta() {
		return idmulta;
	}

	public void setIdmulta(Multa idmulta) {
		this.idmulta = idmulta;
	}

	public Paciente getIdpaciente() {
		return idpaciente;
	}

	public void setIdpaciente(Paciente idpaciente) {
		this.idpaciente = idpaciente;
	}

	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Horario getIdhorario() {
		return idhorario;
	}

	public void setIdhorario(Horario idhorario) {
		this.idhorario = idhorario;
	}

	public Solicitud(Long idsolicitud, String fecha, String observaciones, Tiposolicitud tiposolicitud,
			Estadosolicitud idestado, Multa idmulta, Paciente idpaciente,
			Horario idhorario) {
		super();
		this.idsolicitud = idsolicitud;
		this.fecha = fecha;
		this.observaciones = observaciones;
		this.tiposolicitud = tiposolicitud;
		this.idestado = idestado;
		this.idmulta = idmulta;
		this.idpaciente = idpaciente;
		this.idhorario = idhorario;
	}

	public Solicitud() {
		super();
	}

	@Override
	public String toString() {
		return "Solicitud [idsolicitud=" + idsolicitud + ", fecha=" + fecha + ", observaciones=" + observaciones
				+ ", tiposolicitud=" + tiposolicitud + ", idestado=" + idestado + ", idmulta=" + idmulta
				+ ", idpaciente=" + idpaciente + ", idhorario=" + idhorario
				+ "]";
	}
	
	

	
	


	
	
}
