/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.domain.model;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@ToString(exclude = { "paciente", "medico" })
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "informes")
public class Informe implements Serializable {

	private static final long serialVersionUID = 2L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Basic(optional = false)
	@Column(name = "nombre_inf")
	private String nombreInf;
	@Basic(optional = false)
	@Column(name = "url", unique = true)
	private String url;
	@JoinColumn(name = "nss", referencedColumnName = "nss")
	@ManyToOne(optional = false)
	private Paciente paciente;
	@JoinColumn(name = "n_licencia", referencedColumnName = "n_licencia")
	@ManyToOne(optional = false)
	private Medico medico;

}
