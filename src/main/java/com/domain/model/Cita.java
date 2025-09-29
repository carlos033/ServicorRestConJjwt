/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 *
 * @author ck
 */
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
@Entity
@Table(name = "cita",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"n_licencia", "f_hora_cita"}, name = "UK_medico_cita"),
        @UniqueConstraint(columnNames = {"nss", "f_hora_cita"}, name = "UK_paciente_cita")})
public class Cita implements Serializable {

  private static final long serialVersionUID = 5L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "f_hora_cita", nullable = false)
  private LocalDateTime fechaCita;

  @JoinColumn(name = "nss", referencedColumnName = "nss")
  @ManyToOne(optional = false)
  @With
  private Paciente paciente;

  @JoinColumn(name = "n_licencia", referencedColumnName = "n_licencia")
  @ManyToOne(optional = false)
  @With
  private Medico medico;
}
