package com.gco.proyect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gco.proyect.dao.EstadosolicitudDAO;
import com.gco.proyect.model.Sesion;

@RestController
@RequestMapping("estadosolicitud")
public class EstadosolicitudCtlr {

	@Autowired
	private EstadosolicitudDAO estadosolicitud;
	
	
	
}
