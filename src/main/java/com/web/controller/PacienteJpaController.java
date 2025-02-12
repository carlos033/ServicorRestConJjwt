/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.web.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.application.service.ServiciosPacienteI;
import com.domain.dto.PacienteDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@RestController
@RequestMapping(path = "/pacientes")
public class PacienteJpaController {

	private final ServiciosPacienteI sPaciente;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Void> aniadirPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
		sPaciente.savePaciente(pacienteDTO);
		URI location = URI.create("/pacientes/" + pacienteDTO.nss());
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/{nSS}")
	public ResponseEntity<Void> eliminarPaciente(@PathVariable String nSS) {
		sPaciente.eliminarPaciente(nSS);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<PacienteDTO>> listPacientes() {
		return ResponseEntity.ok(sPaciente.buscarTodosP());
	}

	@GetMapping("/{nSS}")
	public ResponseEntity<PacienteDTO> buscarPaciente(@RequestParam String nSS) {
		return ResponseEntity.ok(sPaciente.buscarPaciente(nSS));
	}

	@GetMapping("/{nLicencia}/pacientes")
	public ResponseEntity<List<PacienteDTO>> buscarPacienteXMedico(@PathVariable String nLicencia) {
		return ResponseEntity.ok(sPaciente.buscarPacientesXMedico(nLicencia));
	}
}
