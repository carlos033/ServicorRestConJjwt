/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.infrastructure.controller;

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

import com.application.service.ServicioInforme;
import com.domain.dto.InformeDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@RestController
@RequestMapping(path = "/informes")
public class InformesJpaController {

	private final ServicioInforme sInformes;

	@PostMapping
	public ResponseEntity<InformeDTO> aniadirInforme(@Valid @RequestBody InformeDTO dTO) {
		long id = sInformes.crearInforme(dTO);
		URI location = URI.create("/medicos/" + id);
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/{nombre}")
	public ResponseEntity<Void> eliminarInforme(@PathVariable long id) {
		sInformes.eliminarInforme(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<InformeDTO>> listInformes() {
		return ResponseEntity.ok(sInformes.buscarTodosI());
	}

	@GetMapping("/{nSS}/informes")
	public ResponseEntity<List<InformeDTO>> buscarInformesXPaciente(@PathVariable String nSS) {
		return ResponseEntity.ok(sInformes.buscarInformesXPaciente(nSS));
	}

}
