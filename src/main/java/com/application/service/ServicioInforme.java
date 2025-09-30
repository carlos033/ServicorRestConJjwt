/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.application.service;

import java.util.List;

import com.domain.dto.InformeDTO;

/**
 *
 * @author ck
 */
public interface ServicioInforme {

  List<InformeDTO> buscarTodosI();

  void eliminarInforme(long id);

  List<InformeDTO> buscarInformesXPaciente(String nSS);

  List<InformeDTO> buscarInformesXMedico(String nLicencia);

  long crearInforme(InformeDTO dto);

}
