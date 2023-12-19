/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class MedicoDTO implements Serializable {

	private static final long serialVersionUID = 9L;
	private String nLicencia;
	private String nombre;
	private String especialidad;
	private int consulta;
	private String password;
	@JsonIgnoreProperties("listaMedicos")
	private HospitalDTO hospital;
	private List<CitaDTO> listaCitas;
	private List<InformeDTO> listaInformes;

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
}
