/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.domain.model.Medico;
import com.domain.model.Paciente;

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

	String SQL_BUSCAR_PACIENTES_POR_MEDICO = """
	        SELECT p
	        FROM Paciente
	        p JOIN
	        p.listaCitas pacientecitas
	        JOIN pacientecitas.
	        medico m
	        WHERE m.numLicencia=:nLicencia""";

	String SQL_BUSCAR_MEDICO_POR_ESPECIALIDAD_HOSPITAL = """
	        Select m
	        from Medico as m
	        where m.especialidad = :especialidad and m.hospital.id = :idHospital""";

	String SQL_BUSCAR_MEDICOS_POR_HOSPITAL = """
	        Select m
	        from Medico as m
	        where m.hospital.id = :idHospital""";

	@Query(SQL_BUSCAR_PACIENTES_POR_MEDICO)
	List<Paciente> buscarPacientesXMedico(@Param("nLicencia") String nLicencia);

	@Query(SQL_BUSCAR_MEDICO_POR_ESPECIALIDAD_HOSPITAL)
	List<Medico> buscarMedicoXEspecialidad(@Param("especialidad") String especialidad, @Param("idHospital") long idHospital);

	@Query(SQL_BUSCAR_MEDICOS_POR_HOSPITAL)
	List<Medico> buscarMedicosXHospital(@Param("idHospital") long idHospital);

	@Query(SQL_BUSCAR_MEDICO_CABECERA)
	Optional<Medico> buscarMmedico(@Param("nSS") String nSS);
}
