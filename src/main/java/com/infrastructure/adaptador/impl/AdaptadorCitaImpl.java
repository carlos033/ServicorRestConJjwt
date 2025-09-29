package com.infrastructure.adaptador.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    LocalDateTime fecha = dto.fechaCita();

    if (!fecha.isAfter(LocalDateTime.now())) {
      throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "La fecha debe ser posterior a hoy");
    }

    validarDisponibilidad(dto);

    Medico medico = medicoRepository.findById(dto.medico().numLicencia()).orElseThrow(
        () -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El número de licencia no existe"));

    Paciente paciente = pacienteRepository.findById(dto.paciente().nss()).orElseThrow(
        () -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El número de SS no existe"));

    Cita cita = mapperCita.toEntityCita(dto).withMedico(medico).withPaciente(paciente);
    return citaRepository.save(cita).getId();
  }

  private void validarDisponibilidad(CitaDTO dto) {
    citaRepository.findByPacienteNssAndFechaCita(dto.paciente().nss(), dto.fechaCita())
        .ifPresent(c -> throwConflict("Usted ya tiene una cita en esa fecha y hora"));

    citaRepository.findByMedicoNumLicenciaAndFechaCita(dto.medico().numLicencia(), dto.fechaCita())
        .ifPresent(c -> throwConflict("El médico no tiene hueco a esa hora ese día"));
  }

  private void throwConflict(String message) {
    throw new ExcepcionServicio(HttpStatus.CONFLICT, message);
  }

  @Override
  public List<CitaDTO> buscarTodasC() {
    return citaRepository.findAll().stream().map(mapperCita::toDTOCita).toList();
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
    return Optional.ofNullable(citaRepository.buscarCitaXPaciente(nSS)).filter(l -> !l.isEmpty())
        .orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "el nSS no existe")).stream()
        .map(mapperCita::toDTOCita).toList();
  }

  @Override
  public List<CitaDTO> buscarXMedico(String nLicencia) {
    List<Cita> listaCitas = citaRepository.findByMedicoNumLicencia(nLicencia);
    if (listaCitas.isEmpty()) {
      throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "el numero de licencia no existe");
    }
    return listaCitas.stream().map(mapperCita::toDTOCita).toList();
  }

  @Override
  public CitaDTO buscarXId(long id) {
    return mapperCita.toDTOCita(citaRepository.findById(id)
        .orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "La ID no existe")));
  }
}
