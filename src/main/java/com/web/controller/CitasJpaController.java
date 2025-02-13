/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.web.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.service.ServiciosCitaI;
import com.domain.dto.CitaDTO;
import com.domain.exception.ExcepcionServicio;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@RestController
@RequestMapping("/citas")
public class CitasJpaController {

	private final ServiciosCitaI sCita;

	@GetMapping()
	public ResponseEntity<List<CitaDTO>> listarCitas() {
		return ResponseEntity.ok(sCita.buscarTodasC());
	}

	@PostMapping
	public ResponseEntity<CitaDTO> aniadirCita(@Valid @RequestBody CitaDTO citaDto) {
		long id = sCita.crearCita(citaDto);
		URI location = URI.create("/citas/" + id);
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarCita(@PathVariable long id) throws ExcepcionServicio {
		sCita.eliminarCita(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CitaDTO> buscarXId(@PathVariable long id) throws ExcepcionServicio {
		return ResponseEntity.ok(sCita.buscarXId(id));
	}

	@GetMapping("/medico//{nLicencia}")
	public ResponseEntity<List<CitaDTO>> buscarCitaXMedico(@PathVariable String nLicencia) {
		return ResponseEntity.ok(sCita.buscarXMedico(nLicencia));

	}

	@GetMapping("/citas/{nSS}")
	public ResponseEntity<List<CitaDTO>> buscarCitasPaciente(@PathVariable String nSS) {
		return ResponseEntity.ok(sCita.buscarXPaciente(nSS));
	}
}
