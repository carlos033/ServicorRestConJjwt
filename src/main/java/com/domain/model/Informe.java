/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.domain.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author ck
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "informes")
public class Informe implements Serializable {

  private static final long serialVersionUID = 2L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private long id;

  @Column(name = "nombre_inf", nullable = false)
  @EqualsAndHashCode.Include
  private String nombreInf;

  @Column(name = "url", unique = true, nullable = false)
  private String url;

  @JoinColumn(name = "nss", referencedColumnName = "nss")
  @ManyToOne(optional = false)
  @EqualsAndHashCode.Include
  private Paciente paciente;

  @JoinColumn(name = "n_licencia", referencedColumnName = "n_licencia")
  @ManyToOne(optional = false)
  @EqualsAndHashCode.Include
  private Medico medico;

}
