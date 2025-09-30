/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.application.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.application.service.ServicioPaciente;
import com.domain.dto.PacienteDTO;
import com.infrastructure.adaptador.impl.AdaptadorPacienteImpl;

import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@Service
public class ServiciosPaciente implements ServicioPaciente {

  private AdaptadorPacienteImpl adaptadorPaciente;

  @Override
  public List<PacienteDTO> buscarTodosP() {
    return adaptadorPaciente.buscarTodosP();
  }

  @Override
  public void eliminarPaciente(String nSS) {
    adaptadorPaciente.eliminarPaciente(nSS);
  }

  @Override
  public void savePaciente(PacienteDTO dto) {
    adaptadorPaciente.savePaciente(dto);
  }

  @Override
  public PacienteDTO buscarPaciente(String nSS) {
    return adaptadorPaciente.buscarPaciente(nSS);
  }

  @Override
  public List<PacienteDTO> buscarPacientesXMedico(String nLicencia) {
    return adaptadorPaciente.buscarPacientesXMedico(nLicencia);
  }
}
