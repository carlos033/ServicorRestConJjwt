package com.infrastructure.adaptador.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.application.adaptador.AdaptadorPaciente;
import com.domain.dto.PacienteDTO;
import com.domain.exception.ExcepcionServicio;
import com.domain.model.Paciente;
import com.infrastructure.mapper.MappersPaciente;
import com.infrastructure.repository.MedicoRepository;
import com.infrastructure.repository.PacienteRepository;

import lombok.AllArgsConstructor;

@Transactional
@Component
@AllArgsConstructor
public class AdaptadorPacienteImpl implements AdaptadorPaciente {

	private final MappersPaciente mapperPaciente;

	private final PacienteRepository pacienteRepository;

	private final MedicoRepository medicoRepository;

	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public List<PacienteDTO> buscarTodosP() {
		return pacienteRepository.findAll().stream().map(paciente -> mapperPaciente.toDTOPaciente(paciente)).toList();
	}

	@Override
	public void eliminarPaciente(String nSS) {
		if (!pacienteRepository.existsById(nSS)) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "El número de SS no existe");
		}
		pacienteRepository.deleteById(nSS);
	}

	@Override
	public void savePaciente(PacienteDTO dto) {
		Paciente paciente = mapperPaciente.toEntityPaciente(dto);
		paciente.setPassword(passwordEncoder.encode(dto.password()));
		pacienteRepository.save(paciente);
	}

	@Override
	public PacienteDTO buscarPaciente(String nSS) throws ExcepcionServicio {
		return mapperPaciente.toDTOPaciente(pacienteRepository.findById(nSS).orElse(null));
	}

	@Override
	public List<PacienteDTO> buscarPacientesXMedico(String nLicencia) {
		List<PacienteDTO> listaPacientesDTO = medicoRepository.buscarPacientesXMedico(nLicencia).stream().map(mapperPaciente::toDTOPaciente).toList();

		if (listaPacientesDTO.isEmpty()) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "El numero de Licencia no existe");
		}
		return listaPacientesDTO;
	}
}
