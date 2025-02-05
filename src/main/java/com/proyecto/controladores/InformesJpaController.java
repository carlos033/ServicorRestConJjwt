/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.proyecto.controladores;

import java.util.List;
import java.util.stream.Collectors;

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

import com.proyecto.dto.InformeCompletoDTO;
import com.proyecto.dto.InformeDTO;
import com.proyecto.dto.InformeMedicoDTO;
import com.proyecto.modelos.Informe;
import com.proyecto.serviciosI.ServiciosInformeI;
import com.proyecto.utiles.Transformadores;

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

	private final Transformadores transformador;

	private final ServiciosInformeI sInformes;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<InformeMedicoDTO> aniadirInforme(@Valid @RequestBody InformeCompletoDTO informeDto) {
		Informe convertedInforme = transformador.convertirAEntidadI(informeDto);
		sInformes.crearInforme(convertedInforme);
		return ResponseEntity.status(HttpStatus.CREATED).body(transformador.convertirADTOIM(convertedInforme));
	}

	@DeleteMapping("/{nombre}")
	public ResponseEntity<Void> eliminarInforme(@PathVariable String nombre) {
		sInformes.eliminarInforme(nombre);
		return ResponseEntity.noContent().build();

	}

	@GetMapping
	public ResponseEntity<List<InformeDTO>> listInformes() {
		return ResponseEntity.ok(sInformes.buscarTodosI().stream().map(transformador::convertirADTOI).collect(Collectors.toList()));
	}

}
