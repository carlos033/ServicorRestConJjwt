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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "hospital")
public class Hospital implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "nombre_hos")
	private String nombreHos;
	@Basic(optional = false)
	@Column(name = "poblacion")
	private String poblacion;
	@Basic(optional = false)
	@Column(name = "nomero_consultas")
	private String numConsultas;
	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.MERGE }, mappedBy = "hospital")
	private List<Medico> listaMedicos;

}
