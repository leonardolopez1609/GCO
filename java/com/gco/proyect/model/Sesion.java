package com.gco.proyect.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="estadosolicitud")
public class Sesion {
	
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idpaciente;

private String user;
private String pass;
public Sesion(String user, String pass) {
	super();
	this.user = user;
	this.pass = pass;
}
public Sesion() {
	super();
}
public String getUser() {
	return user;
}
public void setUser(String user) {
	this.user = user;
}
public String getPass() {
	return pass;
}
public void setPass(String pass) {
	this.pass = pass;
}
@Override
public String toString() {
	return "Sesion [user=" + user + ", pass=" + pass + "]";
}
public static long getSerialversionuid() {
	return serialVersionUID;
}
	
	
	
}
