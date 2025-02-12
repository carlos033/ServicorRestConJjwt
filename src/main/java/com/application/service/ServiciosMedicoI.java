/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.application.service;

import java.util.List;

import com.domain.dto.MedicoDTO;
import com.domain.exception.ExcepcionServicio;

/**
 *
 * @author ck
 */
public interface ServiciosMedicoI {

	List<MedicoDTO> buscarTodosM();

	String saveMedico(MedicoDTO dto);

	void eliminarMedico(String nLicencia);

	MedicoDTO buscarMedico(String nLicencia);

	List<MedicoDTO> buscarMedicoXEspecialidad(String especialidad, long hospital);

	List<MedicoDTO> buscarMedicosXHospital(long hospital) throws ExcepcionServicio;

}
