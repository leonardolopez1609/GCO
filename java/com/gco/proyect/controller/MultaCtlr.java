package com.gco.proyect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.gco.proyect.dao.MultaDAO;

@Controller
@RequestMapping("multa")
public class MultaCtlr {

	@Autowired
	private MultaDAO multa;
}
