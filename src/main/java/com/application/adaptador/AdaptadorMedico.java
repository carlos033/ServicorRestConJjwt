package com.application.adaptador;

import java.util.List;

import com.domain.dto.MedicoDTO;
import com.domain.exception.ExcepcionServicio;

public interface AdaptadorMedico {

	List<MedicoDTO> buscarTodosM();

	String saveMedico(MedicoDTO dto);

	void eliminarMedico(String nLicencia);

	MedicoDTO buscarMedico(String nLicencia);

	List<MedicoDTO> buscarMedicoXEspecialidad(String especialidad, long idHospital) throws ExcepcionServicio;

	List<MedicoDTO> buscarMedicosXHospital(long idHospital);

	MedicoDTO buscarMiMedico(String nSS);

}
