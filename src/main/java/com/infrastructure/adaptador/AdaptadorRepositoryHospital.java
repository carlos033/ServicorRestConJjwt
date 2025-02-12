package com.infrastructure.adaptador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.domain.dto.HospitalDTO;
import com.domain.exception.ExcepcionServicio;
import com.domain.model.Hospital;
import com.infrastructure.mapper.MappersHospital;
import com.infrastructure.repository.HospitalRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AdaptadorRepositoryHospital {
	private HospitalRepository repositorioH;

	private MappersHospital mapperHospital;

	public List<HospitalDTO> buscarTodosH() {
		return repositorioH.findAll().stream().map(hospital -> mapperHospital.toDTOHospital(hospital)).toList();
	}

	public long save(HospitalDTO dto) {
		Hospital hospital = mapperHospital.toEntityHospital(dto);
		repositorioH.save(hospital);
		return hospital.getId();
	}

	public void eliminarHospital(long id) {
		repositorioH.findById(id).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El hospital no existe"));
		repositorioH.deleteById(id);
	}

	public HospitalDTO buscarHospital(long id) throws ExcepcionServicio {
		return mapperHospital.toDTOHospital(repositorioH.findById(id).get());
	}
}
