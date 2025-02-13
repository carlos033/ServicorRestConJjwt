/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.application.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.service.ServicioCita;
import com.domain.dto.CitaDTO;
import com.domain.exception.ExcepcionServicio;
import com.infrastructure.adaptador.impl.AdaptadorCitaImpl;

import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@Service("ServiciosCitaI")
@Transactional
public class ServiciosCita implements ServicioCita {

	private final AdaptadorCitaImpl adaptador;

	@Override
	public long crearCita(CitaDTO dto) throws ExcepcionServicio {
		return adaptador.crearCita(dto);
	}

	@Override
	public List<CitaDTO> buscarTodasC() {
		return adaptador.buscarTodasC();
	}

	@Override
	public void eliminarCita(long id) throws ExcepcionServicio {
		adaptador.eliminarCita(id);
	}

	@Override
	public List<CitaDTO> buscarXPaciente(String nSS) throws ExcepcionServicio {
		return adaptador.buscarXPaciente(nSS);
	}

	@Override
	public List<CitaDTO> buscarXMedico(String nLicencia) throws ExcepcionServicio {
		return adaptador.buscarXMedico(nLicencia);
	}

	@Override
	public CitaDTO buscarXId(long id) throws ExcepcionServicio {
		return adaptador.buscarXId(id);
	}
}
