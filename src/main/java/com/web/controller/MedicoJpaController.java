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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.application.service.ServiciosMedicoI;
import com.domain.dto.MedicoDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@RestController
@RequestMapping(path = "/medicos")
public class MedicoJpaController {

	private final ServiciosMedicoI sMedico;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Void> aniadirMedico(@Valid @RequestBody MedicoDTO medicoDTO) {
		String nLicencia = sMedico.saveMedico(medicoDTO);
		URI location = URI.create("/medicos/" + nLicencia);
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/{nLicencia}")
	public ResponseEntity<Void> eliminarMedico(@PathVariable String nLicencia) {
		sMedico.eliminarMedico(nLicencia);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{nLicencia}")
	public ResponseEntity<MedicoDTO> buscarMedico(@PathVariable String nLicencia) {
		return ResponseEntity.ok(sMedico.buscarMedico(nLicencia));
	}

	@GetMapping
	public ResponseEntity<List<MedicoDTO>> listMedicos() {
		return ResponseEntity.ok(sMedico.buscarTodosM());
	}

	@GetMapping("/especialidad/{especialidad}/hospital/{idHospital}")
	public ResponseEntity<List<MedicoDTO>> buscarMedicoXEspecialidad(@PathVariable String especialidad, @PathVariable long idHospital) {
		return ResponseEntity.ok(sMedico.buscarMedicoXEspecialidad(especialidad, idHospital));
	}

	@GetMapping("/hospital/{idHospital}")
	public ResponseEntity<List<MedicoDTO>> buscarMedicosXHospital(@PathVariable long idHospital) {
		return ResponseEntity.ok(sMedico.buscarMedicosXHospital(idHospital));
	}
}
