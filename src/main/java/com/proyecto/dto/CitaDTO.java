/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CitaDTO implements Serializable {

	private static final long serialVersionUID = 6L;
	private Integer id;
	private Date fHoraCita;
	@JsonIgnoreProperties("citas")
	private PacienteDTO paciente;
	@JsonIgnoreProperties("listaCitas")
	private MedicoDTO medico;

	public CitaDTO(Date fHoraCita, PacienteDTO paciente, MedicoDTO medico) {
		this.fHoraCita = fHoraCita;
		this.paciente = paciente;
		this.medico = medico;
	}

}
