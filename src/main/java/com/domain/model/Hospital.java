/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.domain.model;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "hospital")
public class Hospital implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @EqualsAndHashCode.Include
  private long id;

  @Column(name = "nombre_hos", nullable = false)
  @EqualsAndHashCode.Include
  private String nombreHos;

  @Column(name = "poblacion", nullable = false)
  @EqualsAndHashCode.Include
  private String poblacion;

  @Column(name = "numero_consultas", nullable = false)
  private String numConsultas;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "hospital")
  private List<Medico> listaMedicos;

}
