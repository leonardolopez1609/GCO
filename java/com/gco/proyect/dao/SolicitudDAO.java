package com.gco.proyect.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gco.proyect.model.Solicitud;

public interface SolicitudDAO extends JpaRepository<Solicitud, Long> {

}
