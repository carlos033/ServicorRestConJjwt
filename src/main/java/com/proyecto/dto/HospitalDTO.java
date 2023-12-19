/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.dto;

import java.io.Serializable;
import java.util.List;

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
public class HospitalDTO implements Serializable {

	private static final long serialVersionUID = 7L;
	private String nombreHos;
	private String poblacion;
	private String numConsultas;
	private List<MedicoDTO> listaMedicos;

	public HospitalDTO(String nombreHos, String poblacion, List<MedicoDTO> listaMedicos, String numConsultas) {
		this.nombreHos = nombreHos;
		this.poblacion = poblacion;
		this.listaMedicos = listaMedicos;
		this.numConsultas = numConsultas;
	}

}
