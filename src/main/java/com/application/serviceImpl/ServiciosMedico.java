/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.application.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.application.service.ServiciosMedicoI;
import com.domain.dto.MedicoDTO;
import com.domain.exception.ExcepcionServicio;
import com.infrastructure.adaptador.AdaptadorRepositoryMedico;

import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@Service
public class ServiciosMedico implements ServiciosMedicoI {

	private AdaptadorRepositoryMedico adaptador;

	@Override
	public List<MedicoDTO> buscarTodosM() {
		return adaptador.buscarTodosM();
	}

	@Override
	public String saveMedico(MedicoDTO dto) {
		return adaptador.saveMedico(dto);
	}

	@Override
	public void eliminarMedico(String nLicencia) {
		adaptador.eliminarMedico(nLicencia);
	}

	@Override
	public MedicoDTO buscarMedico(String nLicencia) {
		return adaptador.buscarMedico(nLicencia);
	}

	@Override
	public List<MedicoDTO> buscarMedicoXEspecialidad(String especialidad, long hospital) {
		return adaptador.buscarMedicoXEspecialidad(especialidad, hospital);
	}

	@Override
	public List<MedicoDTO> buscarMedicosXHospital(long hospital) throws ExcepcionServicio {
		return adaptador.buscarMedicosXHospital(hospital);
	}
}
