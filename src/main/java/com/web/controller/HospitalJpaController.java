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

import com.application.service.ServiciosHospitalI;
import com.domain.dto.HospitalDTO;
import com.domain.exception.ExcepcionServicio;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@RestController
@RequestMapping(path = "/hospitales")
public class HospitalJpaController {

	private ServiciosHospitalI sHospital;

	@PostMapping
	public ResponseEntity<HospitalDTO> aniadirHospital(@Valid @RequestBody HospitalDTO hospitalDto) {
		long id = sHospital.save(hospitalDto);
		URI location = URI.create("/hospitales/" + id);

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/{nombre}")
	public ResponseEntity<Void> eliminarHospital(@PathVariable long id) throws ExcepcionServicio {
		sHospital.eliminarHospital(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<HospitalDTO>> listarhospitales() {
		return ResponseEntity.ok(sHospital.buscarTodosH());
	}

	@GetMapping("{nombre}")
	public ResponseEntity<HospitalDTO> buscarHospital(@PathVariable long id) throws ExcepcionServicio {
		return ResponseEntity.ok(sHospital.buscarHospital(id));
	}
}
