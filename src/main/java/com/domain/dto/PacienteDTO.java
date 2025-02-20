/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.domain.model.Logable;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Pattern;

/**
 *
 * @author ck
 */
public record PacienteDTO(@Pattern(regexp = "^ES.*$", message = "El número de Seguridad Social debe empezar con 'ES'") String nss, String nombre, String password,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") LocalDate fechaNacimiento, List<CitaDTO> listaCitas, List<InformeDTO> listaInformes) implements Serializable, Logable {
	@Override
	public String getIdentifier() {
		return nss;
	}
}
