/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.modelos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Table(name = "paciente")
public class Paciente implements Serializable, Logable {

	private static final long serialVersionUID = 4L;
	@Id
	@Basic(optional = false)
	@Column(name = "nss")
	private String nSS;
	@Basic(optional = false)
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "password")
	private String password;
	@Basic(optional = false)
	@Column(name = "f_nacimiento")
	@Temporal(TemporalType.DATE)
	private Date fNacimiento;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente")
	private List<Cita> listaCitas;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente")
	private List<Informe> informes;

	@Override
	public String getIdentifier() {
		return getNSS();
	}

}
