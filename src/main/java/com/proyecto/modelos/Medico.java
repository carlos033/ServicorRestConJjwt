/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.modelos;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
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
@Entity
@Table(name = "medico")
public class Medico implements Serializable, Logable {

	private static final long serialVersionUID = 3L;
	@Id
	@Size(max = 12)
	@Basic(optional = false)
	@Column(name = "n_licencia")
	private String nLicencia;
	@Basic(optional = false)
	@Column(name = "nombre")
	private String nombre;
	@Basic(optional = false)
	@Column(name = "especialidad")
	private String especialidad;
	@Basic(optional = false)
	@Column(name = "consulta")
	private int consulta;
	@Column(name = "password", nullable = false)
	private String password;
	@JoinColumn(name = "nombre_hos")
	@ManyToOne(optional = true)
	private Hospital hospital;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "medico")
	private List<Cita> listaCitas;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "medico")
	private List<Informe> listaInformes;

	@Override
	public String getIdentifier() {
		return getnLicencia();
	}

	public String getnLicencia() {
		return nLicencia;
	}

	public void setnLicencia(String nLicencia) {
		this.nLicencia = nLicencia;
	}
}
