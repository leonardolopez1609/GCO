package com.gco.proyect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gco.proyect.dao.SolicitudDAO;

@RestController
@RequestMapping("solicitud")
public class SolicitudCtlr {

	@Autowired
	private SolicitudDAO solicitud;
}
