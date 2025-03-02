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

	List<Cita> findByMedicoNumLicencia(@Param("nLicencia") String nLicencia);

	Optional<Cita> findByPacienteNssAndFechaCita(@Param("nss") String nSS, @Param("fecha") LocalDateTime fecha);

	Optional<Cita> findByMedicoNumLicenciaAndFechaCita(@Param("nLicencia") String nLicencia, @Param("fecha") LocalDateTime fecha);

}
