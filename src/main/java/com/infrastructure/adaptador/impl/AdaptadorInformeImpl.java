package com.infrastructure.adaptador.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.adaptador.AdaptadorInforme;
import com.domain.dto.InformeDTO;
import com.domain.exception.ExcepcionServicio;
import com.domain.model.Informe;
import com.domain.model.Medico;
import com.domain.model.Paciente;
import com.infrastructure.mapper.MappersInforme;
import com.infrastructure.repository.InformeRepository;
import com.infrastructure.repository.MedicoRepository;
import com.infrastructure.repository.PacienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional
public class AdaptadorInformeImpl implements AdaptadorInforme {

	private final MappersInforme mapperInforme;

	private final InformeRepository informeRepository;

	private final PacienteRepository pacienteRepository;

	private final MedicoRepository medicoRepository;

	@Override
	public List<InformeDTO> buscarTodosI() {
		return informeRepository.findAll().stream().map(informe -> mapperInforme.toDTOInforme(informe)).toList();
	}

	@Override
	public void eliminarInforme(long id) {
		informeRepository.findById(id).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El informe no existe"));
		informeRepository.deleteById(id);
	}

	@Override
	public List<InformeDTO> buscarInformesXPaciente(String nSS) {
		pacienteRepository.findById(nSS).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El numero de SS no existe"));
		return informeRepository.buscarInformeXPaciente(nSS).stream().map(informe -> mapperInforme.toDTOInforme(informe)).toList();
	}

	@Override
	public List<InformeDTO> buscarInformesXMedico(String nLicencia) {
		medicoRepository.findById(nLicencia).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El numero de Licencia no existe"));
		return informeRepository.buscarInformeXMedico(nLicencia).stream().map(informe -> mapperInforme.toDTOInforme(informe)).toList();
	}

	@Override
	public long crearInforme(InformeDTO dto) {
		Medico medico = medicoRepository.findById(dto.medico().numLicencia()).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El numero de Licencia no existe"));
		Paciente paciente = pacienteRepository.findById(dto.paciente().nss()).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El numero de SS no existe"));
		Informe entity = mapperInforme.toEntityInforme(dto, paciente, medico);
		informeRepository.save(entity);
		return entity.getId();
	}
}
