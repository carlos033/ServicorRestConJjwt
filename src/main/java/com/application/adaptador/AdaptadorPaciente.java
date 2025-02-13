package com.application.adaptador;

import java.util.List;

import com.domain.dto.PacienteDTO;
import com.domain.exception.ExcepcionServicio;

public interface AdaptadorPaciente {

	List<PacienteDTO> buscarPacientesXMedico(String nLicencia);

	PacienteDTO buscarPaciente(String nSS) throws ExcepcionServicio;

	void savePaciente(PacienteDTO dto);

	void eliminarPaciente(String nSS);

	List<PacienteDTO> buscarTodosP();

}
