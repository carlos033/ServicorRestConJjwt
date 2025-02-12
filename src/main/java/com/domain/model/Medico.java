/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.domain.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author ck
 */
@ToString(exclude = { "hospital", "listaCitas", "listaInformes" })
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "medico")
public class Medico implements Serializable, Logable {

	private static final long serialVersionUID = 3L;
	@Id
	@Size(max = 12)
	@Basic(optional = false)
	@Column(name = "n_licencia")
	private String numLicencia;
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
	@JoinColumn(name = "id")
	@ManyToOne(optional = true)
	private Hospital hospital;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "medico")
	private List<Cita> listaCitas;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "medico")
	private List<Informe> listaInformes;

	@Override
	public String getIdentifier() {
		return getNumLicencia();
	}
}
