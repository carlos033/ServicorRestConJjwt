/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.domain.dto;

import java.io.Serializable;
import java.util.List;

import com.domain.model.Cita;
import com.domain.model.Informe;
import com.domain.model.Logable;

import jakarta.validation.constraints.Pattern;

public record MedicoDTO(
    @Pattern(regexp = "^M.*$",
        message = "El número de licencia debe empezar con 'M'") String numLicencia,
    String nombre, String especialidad, int consulta, String password, HospitalDTO hospital,
    List<Cita> listaCitas, List<Informe> listaInformes) implements Serializable, Logable {
  @Override
  public String getIdentifier() {
    return numLicencia;
  }
}
