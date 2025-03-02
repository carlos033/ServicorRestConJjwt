/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.domain.model.Informe;

/**
 *
 * @author ck
 */

public interface InformeRepository extends JpaRepository<Informe, Long> {

	List<Informe> findByPacienteNss(@Param("nSS") String nSS);

	List<Informe> findByMedicoNumLicencia(@Param("nLicencia") String nLicencia);
}
