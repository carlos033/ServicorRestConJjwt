package com.infrastructure.adaptador.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.application.adaptador.AdaptadorHospital;
import com.domain.dto.HospitalDTO;
import com.domain.exception.ExcepcionServicio;
import com.domain.model.Hospital;
import com.infrastructure.mapper.MappersHospital;
import com.infrastructure.repository.HospitalRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@Transactional
public class AdaptadorHospitalImpl implements AdaptadorHospital {

	private final HospitalRepository repositorioH;

	private final MappersHospital mapperHospital;

	@Override
	public List<HospitalDTO> buscarTodosH() {
		return repositorioH.findAll().stream().map(hospital -> mapperHospital.toDTOHospital(hospital)).toList();
	}

	@Override
	public long save(HospitalDTO dto) {
		Hospital hospital = mapperHospital.toEntityHospital(dto);
		return repositorioH.save(hospital).getId();
	}

	@Override
	public void eliminarHospital(long id) {
		if (!repositorioH.existsById(id)) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "El ide de ese hospital no existe");
		}
		repositorioH.deleteById(id);
	}

	@Override
	public HospitalDTO buscarHospital(long id) throws ExcepcionServicio {
		return mapperHospital.toDTOHospital(repositorioH.findById(id).orElse(null));
	}
}
