package com.application.adaptador;

import java.util.List;

import com.domain.dto.CitaDTO;

public interface AdaptadorCita {

	long crearCita(CitaDTO dto);

	List<CitaDTO> buscarTodasC();

	void eliminarCita(long id);

	List<CitaDTO> buscarXPaciente(String nSS);

	List<CitaDTO> buscarXMedico(String nLicencia);

	CitaDTO buscarXId(long id);

}
