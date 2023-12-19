/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.dto;

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
public class InformeCompletoDTO {

	private String url;
	private String nombreInf;
	private MedicoDTO medico;
	private PacienteDTO paciente;

}
