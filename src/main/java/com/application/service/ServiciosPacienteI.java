/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.application.service;

import java.util.List;

import com.domain.dto.PacienteDTO;

/**
 *
 * @author ck
 */
public interface ServiciosPacienteI {

	List<PacienteDTO> buscarTodosP();

	void savePaciente(PacienteDTO dto);

	void eliminarPaciente(String nSS);

	PacienteDTO buscarPaciente(String nSS);

	List<PacienteDTO> buscarPacientesXMedico(String nLicencia);
}
