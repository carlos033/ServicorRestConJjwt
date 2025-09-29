/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.domain.model.Medico;

/**
 *
 * @author ck
 */
public interface MedicoRepository extends JpaRepository<Medico, String> {

  String SQL_BUSCAR_MEDICO_CABECERA = """
      SELECT m FROM Medico m
      JOIN m.listaCitas medicocitas
      JOIN medicocitas.paciente p
      WHERE p.nss = :nSS AND m.especialidad = 'Atencion primaria'
      """;

  List<Medico> findByEspecialidadAndHospitalId(@Param("especialidad") String especialidad,
      @Param("idHospital") long idHospital);

  Page<Medico> findByHospitalId(@Param("idHospital") long idHospital, Pageable pageable);

  @Query(SQL_BUSCAR_MEDICO_CABECERA)
  Optional<Medico> buscarMmedico(@Param("nSS") String nSS);
}
