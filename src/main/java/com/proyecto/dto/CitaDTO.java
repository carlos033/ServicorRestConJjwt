/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.proyecto.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author ck
 */
@JsonIgnoreProperties({ "paciente.citas", "medico.listaCitas" })
public record CitaDTO(Integer id, LocalDateTime fHoraCita, PacienteDTO paciente, MedicoDTO medico) implements Serializable {

	public CitaDTO(LocalDateTime fHoraCita, PacienteDTO paciente, MedicoDTO medico) {
		this(null, fHoraCita, paciente, medico);
	}

}
