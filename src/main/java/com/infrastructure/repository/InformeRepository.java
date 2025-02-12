/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.domain.model.Informe;

/**
 *
 * @author ck
 */

public interface InformeRepository extends JpaRepository<Informe, Long> {

	@Query("Select i from Informe i where i.paciente.nSS = :nSS")
	public List<Informe> buscarInformeXPaciente(@Param("nSS") String nSS);

	@Query("Select i from Informe i where i.medico.nLicencia = :nLicencia")
	public List<Informe> buscarInformeXMedico(@Param("nLicencia") String nLicencia);
}
