/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.domain.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Table(name = "paciente")
public class Paciente implements Serializable {

  private static final long serialVersionUID = 4L;
  @Id
  @Column(name = "nss", nullable = false)
  @EqualsAndHashCode.Include
  private String nss;

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "password")
  private String password;

  @Column(name = "f_nacimiento", nullable = false)
  @Temporal(TemporalType.DATE)
  private LocalDate fechaNacimiento;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "paciente")
  private List<Cita> listaCitas;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "paciente")
  private List<Informe> listaInformes;
}
