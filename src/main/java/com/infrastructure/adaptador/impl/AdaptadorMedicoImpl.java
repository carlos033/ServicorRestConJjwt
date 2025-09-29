package com.infrastructure.adaptador.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.application.adaptador.AdaptadorMedico;
import com.domain.dto.MedicoDTO;
import com.domain.exception.ExcepcionServicio;
import com.domain.model.Medico;
import com.infrastructure.mapper.MappersMedico;
import com.infrastructure.repository.HospitalRepository;
import com.infrastructure.repository.MedicoRepository;

import lombok.AllArgsConstructor;

@Transactional
@Component
@AllArgsConstructor
public class AdaptadorMedicoImpl implements AdaptadorMedico {

  private final MappersMedico mapperMedico;

  private final MedicoRepository medicoRepository;

  private final HospitalRepository hospitalRepository;

  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public List<MedicoDTO> buscarTodosM(Pageable pageable) {
    return medicoRepository.findAll(pageable).stream().map(mapperMedico::toDTOMedico).toList();
  }

  @Override
  public String saveMedico(MedicoDTO dto) {
    Medico medico = mapperMedico.toEntityMedico(dto);
    medico.setPassword(passwordEncoder.encode(medico.getPassword()));
    return medicoRepository.save(medico).getNumLicencia();
  }

  @Override
  public void eliminarMedico(String nLicencia) {
    if (!medicoRepository.existsById(nLicencia)) {
      throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "El número de Licencia no existe");
    }
    medicoRepository.deleteById(nLicencia);
  }

  @Override
  public MedicoDTO buscarMedico(String nLicencia) {
    return mapperMedico.toDTOMedico(medicoRepository.findById(nLicencia).orElse(null));
  }

  @Override
  public List<MedicoDTO> buscarMedicoXEspecialidad(String especialidad, long idHospital)
      throws ExcepcionServicio {
    hospitalRepository.findById(idHospital)
        .orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El hospital no existe"));
    List<MedicoDTO> listaMedicoDTO =
        medicoRepository.findByEspecialidadAndHospitalId(especialidad, idHospital).stream()
            .map(mapperMedico::toDTOMedico).toList();
    if (listaMedicoDTO.isEmpty()) {
      throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "No hay medicos con esa especialidad");
    }
    return listaMedicoDTO;
  }

  @Override
  public Page<MedicoDTO> buscarMedicosXHospital(long idHospital, Pageable pageable) {
    Page<Medico> pageMedicos = medicoRepository.findByHospitalId(idHospital, pageable);
    if (pageMedicos.isEmpty()) {
      throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "No hay medicos en el hospital");
    }
    return pageMedicos.map(mapperMedico::toDTOMedico);
  }

  @Override
  public MedicoDTO buscarMiMedico(String nSS) {
    return mapperMedico.toDTOMedico(medicoRepository.buscarMmedico(nSS)
        .orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND,
            "Paciente con NSS no existe o no tiene citas")));
  }
}
