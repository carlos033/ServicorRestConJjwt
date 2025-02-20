/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.domain.model.Cita;

/**
 *
 * @author ck
 */
public interface CitaRepository extends JpaRepository<Cita, Long> {

	@EntityGraph(attributePaths = { "paciente", "medico" })
	@Query("Select c from Cita c where c.paciente.nss= :nss")
	List<Cita> buscarCitaXPaciente(@Param("nss") String nSS);

	@Query("Select c from Cita c where c.medico.numLicencia = :nLicencia")
	List<Cita> buscarCitaXMedico(@Param("nLicencia") String nLicencia);

	@Query("Select c from Cita c where c.paciente.nss= :nss and c.fechaCita = :fecha")
	Optional<Cita> buscarCitaXPacienteYHora(@Param("nss") String nSS, @Param("fecha") LocalDateTime fecha);

	@Query("Select c from Cita c where c.medico.numLicencia = :nLicencia and c.fechaCita = :fecha")
	Optional<Cita> buscarCitaXMedicoYHora(@Param("nLicencia") String nLicencia, @Param("fecha") LocalDateTime fecha);

}
