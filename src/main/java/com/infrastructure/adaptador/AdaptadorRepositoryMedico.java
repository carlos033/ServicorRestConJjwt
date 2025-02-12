package com.infrastructure.adaptador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.domain.dto.MedicoDTO;
import com.domain.exception.ExcepcionServicio;
import com.domain.model.Medico;
import com.infrastructure.mapper.MappersMedicos;
import com.infrastructure.repository.HospitalRepository;
import com.infrastructure.repository.MedicoRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AdaptadorRepositoryMedico {

	private MappersMedicos mapperMedico;

	private MedicoRepository medicoRepository;

	private HospitalRepository hospitalRepository;

	private BCryptPasswordEncoder passwordEncoder;

	public List<MedicoDTO> buscarTodosM() {
		return medicoRepository.findAll().stream().map(medico -> mapperMedico.toDTOMedico(medico)).toList();
	}

	@Transactional
	public String saveMedico(MedicoDTO dto) {
		Medico medico = mapperMedico.toEntityMedico(dto);
		medico.setPassword(passwordEncoder.encode(medico.getPassword()));
		medicoRepository.save(medico);
		return medico.getNumLicencia();
	}

	public void eliminarMedico(String nLicencia) {
		medicoRepository.findById(nLicencia).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El numero de Licencia no existe"));
		medicoRepository.deleteById(nLicencia);
	}

	public MedicoDTO buscarMedico(String nLicencia) {
		return mapperMedico.toDTOMedico(medicoRepository.findById(nLicencia).get());
	}

	public List<MedicoDTO> buscarMedicoXEspecialidad(String especialidad, long idHospital) throws ExcepcionServicio {
		hospitalRepository.findById(idHospital).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El hospital no existe"));
		List<MedicoDTO> listaMedicoDTO = medicoRepository.buscarMedicoXEspecialidad(especialidad, idHospital).stream().map(mapperMedico::toDTOMedico).toList();
		if (listaMedicoDTO.isEmpty()) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "No hay medicos con esa especialidad");
		}
		return listaMedicoDTO;
	}

	public List<MedicoDTO> buscarMedicosXHospital(long idHospital) {
		List<MedicoDTO> listaMedicoDTO = medicoRepository.buscarMedicosXHospital(idHospital).stream().map(mapperMedico::toDTOMedico).toList();
		if (listaMedicoDTO.isEmpty()) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "No hay medicos en el hospital");
		}
		return listaMedicoDTO;
	}
}
