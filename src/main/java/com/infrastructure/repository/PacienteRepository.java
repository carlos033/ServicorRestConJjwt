/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.infrastructure.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.domain.model.Paciente;

/**
 *
 * @author ck
 */

public interface PacienteRepository extends JpaRepository<Paciente, String> {

  String SQL_BUSCAR_PACIENTES_POR_MEDICO = """
      SELECT p
      FROM Paciente
      p JOIN
      p.listaCitas pacientecitas
      JOIN pacientecitas.
      medico m
      WHERE m.numLicencia=:nLicencia""";

  @Query(SQL_BUSCAR_PACIENTES_POR_MEDICO)
  List<Paciente> buscarPacientesXMedico(@Param("nLicencia") String nLicencia);
}
