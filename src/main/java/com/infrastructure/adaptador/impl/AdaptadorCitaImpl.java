package com.infrastructure.adaptador.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.application.adaptador.AdaptadorCita;
import com.domain.dto.CitaDTO;
import com.domain.exception.ExcepcionServicio;
import com.domain.model.Cita;
import com.domain.model.Medico;
import com.domain.model.Paciente;
import com.infrastructure.mapper.MappersCita;
import com.infrastructure.repository.CitaRepository;
import com.infrastructure.repository.MedicoRepository;
import com.infrastructure.repository.PacienteRepository;

import lombok.AllArgsConstructor;

@Transactional
@Component
@AllArgsConstructor
public class AdaptadorCitaImpl implements AdaptadorCita {

	private final MappersCita mapperCita;

	private final MedicoRepository medicoRepository;

	private final PacienteRepository pacienteRepository;

	private final CitaRepository citaRepository;

	@Override
	public long crearCita(CitaDTO dto) {
		LocalDateTime fechaDHoy = LocalDateTime.now();
		LocalDateTime fecha = dto.fechaCita();
		String nss = dto.paciente().nss();
		String nLicencia = dto.medico().numLicencia();
		citaRepository.findByPacienteNssAndFechaCita(nss, fecha).ifPresent(cita -> {
			throw new ExcepcionServicio(HttpStatus.CONFLICT, "Usted ya tiene una cita en esa fecha y hora");
		});

		citaRepository.findByMedicoNumLicenciaAndFechaCita(nLicencia, fecha).ifPresent(cita -> {
			throw new ExcepcionServicio(HttpStatus.CONFLICT, "El médico no tiene hueco a esa hora ese día");
		});
		if (fecha.isBefore(fechaDHoy)) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "La fecha debe ser posterior a hoy");
		}
		Medico medico = medicoRepository.findById(nLicencia).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El número de licencia no existe"));

		Paciente paciente = pacienteRepository.findById(nss).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El número de SS no existe"));
		Cita cita = mapperCita.toEntityCita(dto);
		cita.setPaciente(paciente);
		cita.setMedico(medico);
		return citaRepository.save(cita).getId();
	}

	@Override
	public List<CitaDTO> buscarTodasC() {
		return citaRepository.findAll().stream().map(cita -> mapperCita.toDTOCita(cita)).toList();
	}

	@Override
	public void eliminarCita(long id) {
		if (!citaRepository.existsById(id)) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "La cita no existe");
		}
		citaRepository.deleteById(id);
	}

	@Override
	public List<CitaDTO> buscarXPaciente(String nSS) {
		List<Cita> listaCitas = citaRepository.buscarCitaXPaciente(nSS);
		if (listaCitas.isEmpty()) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "el nSS no existe");
		}
		return listaCitas.stream().map(cita -> mapperCita.toDTOCita(cita)).toList();
	}

	@Override
	public List<CitaDTO> buscarXMedico(String nLicencia) {
		List<Cita> listaCitas = citaRepository.findByMedicoNumLicencia(nLicencia);
		if (listaCitas.isEmpty()) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "el numero de licencia no existe");
		}
		return listaCitas.stream().map(cita -> mapperCita.toDTOCita(cita)).toList();
	}

	@Override
	public CitaDTO buscarXId(long id) {
		return mapperCita.toDTOCita(citaRepository.findById(id).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "La ID no existe")));
	}
}