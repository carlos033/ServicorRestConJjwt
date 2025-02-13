package com.application.adaptador;

import java.util.List;

import com.domain.dto.InformeDTO;

public interface AdaptadorInforme {

	List<InformeDTO> buscarTodosI();

	void eliminarInforme(long id);

	List<InformeDTO> buscarInformesXPaciente(String nSS);

	List<InformeDTO> buscarInformesXMedico(String nLicencia);

	long crearInforme(InformeDTO dto);

}
