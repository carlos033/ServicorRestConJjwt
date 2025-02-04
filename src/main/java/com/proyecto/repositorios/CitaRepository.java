/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.proyecto.repositorios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.modelos.Cita;
import com.proyecto.modelos.Medico;

/**
 *
 * @author ck
 */
public interface CitaRepository extends JpaRepository<Cita, Integer> {

	String SQL_BUSCAR_MEDICO_CABECERA = """
	        SELECT m FROM Medico m
	        JOIN m.listaCitas medicocitas
	        JOIN medicocitas.paciente p
	        WHERE p.nSS = :nSS AND m.especialidad = 'Atencion primaria'
	        """;

	@EntityGraph(attributePaths = { "paciente", "medico" })
	@Query("Select c from Cita c where c.paciente.nSS= :nss")
	public List<Cita> buscarCitaXPaciente(@Param("nss") String nSS);

	@Query("Select c from Cita c where c.medico.nLicencia = :nLicencia")
	public List<Cita> buscarCitaXMedico(@Param("nLicencia") String nLicencia);

	@Query("Select c from Cita c where c.paciente.nSS= :nss and c.fHoraCita = :fecha")
	public List<Cita> buscarCitaXPacienteYHora(@Param("nss") String nSS, @Param("fecha") Date fecha);

	@Query("Select c from Cita c where c.medico.nLicencia = :nLicencia and c.fHoraCita = :fecha")
	public List<Cita> buscarCitaXMedicoYHora(@Param("nLicencia") String nLicencia, @Param("fecha") Date fecha);

	@Query(SQL_BUSCAR_MEDICO_CABECERA)
	public Optional<Medico> buscarMmedico(@Param("nSS") String nSS);

}
