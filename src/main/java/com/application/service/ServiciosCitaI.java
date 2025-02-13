/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.application.service;

import java.util.List;

import com.domain.dto.CitaDTO;
import com.domain.exception.ExcepcionServicio;

/**
 *
 * @author ck
 */
public interface ServiciosCitaI {

	List<CitaDTO> buscarTodasC();

	List<CitaDTO> buscarXPaciente(String nSS) throws ExcepcionServicio;

	List<CitaDTO> buscarXMedico(String nLicencia) throws ExcepcionServicio;

	void eliminarCita(long id) throws ExcepcionServicio;

	CitaDTO buscarXId(long id) throws ExcepcionServicio;

	long crearCita(CitaDTO dto) throws ExcepcionServicio;

}
