/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.domain.model.Logable;

/**
 *
 * @author ck
 */
public record PacienteDTO(String nss, String nombre, String password, LocalDate fechaNacimiento, List<CitaDTO> listaCitas, List<InformeDTO> listaInformes) implements Serializable, Logable {
	@Override
	public String getIdentifier() {
		return nss;
	}
}
