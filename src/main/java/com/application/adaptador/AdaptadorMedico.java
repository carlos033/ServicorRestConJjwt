package com.application.adaptador;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.domain.dto.MedicoDTO;
import com.domain.exception.ExcepcionServicio;

public interface AdaptadorMedico {

	String saveMedico(MedicoDTO dto);

	void eliminarMedico(String nLicencia);

	MedicoDTO buscarMedico(String nLicencia);

	List<MedicoDTO> buscarMedicoXEspecialidad(String especialidad, long idHospital) throws ExcepcionServicio;

	Page<MedicoDTO> buscarMedicosXHospital(long idHospital, Pageable pageable);

	MedicoDTO buscarMiMedico(String nSS);

	List<MedicoDTO> buscarTodosM(Pageable pageable);

}
