package com.application.adaptador;

import java.util.List;

import com.domain.dto.HospitalDTO;
import com.domain.exception.ExcepcionServicio;

public interface AdaptadorHospital {

	List<HospitalDTO> buscarTodosH();

	long save(HospitalDTO dto);

	void eliminarHospital(long id);

	HospitalDTO buscarHospital(long id) throws ExcepcionServicio;

}
