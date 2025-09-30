/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.application.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.application.service.ServicioInforme;
import com.domain.dto.InformeDTO;
import com.infrastructure.adaptador.impl.AdaptadorInformeImpl;

import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@Service
public class ServiciosInforme implements ServicioInforme {

  private final AdaptadorInformeImpl adaptador;

  @Override
  public List<InformeDTO> buscarTodosI() {
    return adaptador.buscarTodosI();
  }

  @Override
  public void eliminarInforme(long id) {
    adaptador.eliminarInforme(id);
  }

  @Override

  public List<InformeDTO> buscarInformesXPaciente(String nSS) {
    return adaptador.buscarInformesXPaciente(nSS);
  }

  @Override

  public List<InformeDTO> buscarInformesXMedico(String nLicencia) {
    return adaptador.buscarInformesXMedico(nLicencia);
  }

  @Override
  public long crearInforme(InformeDTO dto) {
    return adaptador.crearInforme(dto);
  }
}
