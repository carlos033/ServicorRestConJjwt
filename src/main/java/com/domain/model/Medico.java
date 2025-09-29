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
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
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
@Table(name = "medico")
public class Medico implements Serializable {

  private static final long serialVersionUID = 3L;
  @Id
  @Size(max = 12)
  @Column(name = "n_licencia", nullable = false)
  @EqualsAndHashCode.Include
  private String numLicencia;

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "especialidad", nullable = false)
  private String especialidad;

  @Column(name = "consulta", nullable = true)
  private int consulta;

  @Column(name = "password", nullable = false)
  private String password;

  @JoinColumn(name = "id")
  @ManyToOne
  private Hospital hospital;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "medico")
  private List<Cita> listaCitas;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "medico")
  private List<Informe> listaInformes;
}
