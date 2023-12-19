/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.controladores;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto.dto.CitaDTO;
import com.proyecto.dto.MedicoDTO;
import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Cita;
import com.proyecto.serviciosI.ServiciosCitaI;
import com.proyecto.utiles.Transformadores;

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

	private Transformadores transformador;

	private ServiciosCitaI sCita;

	@GetMapping()
	public List<CitaDTO> listarCitas() {
		return sCita.buscarTodasC().stream().map(transformador::convertirADTOC).collect(Collectors.toList());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CitaDTO aniadirCita(@Valid @RequestBody CitaDTO citaDto) {
		Cita convertedCita = transformador.convertirAEntidadC(citaDto);
		try {
			sCita.crearCita(convertedCita);
		} catch (ExcepcionServicio ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
		}
		return transformador.convertirADTOC(convertedCita);
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminarCita(@PathVariable("id") int id) {
		try {
			sCita.eliminarCita(id);
		} catch (ExcepcionServicio ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
		}
	}

	@GetMapping("/{nSS}/buscarMMedico")
	public MedicoDTO buscarMiMedico(@PathVariable("nSS") String nSS) {
		try {
			return transformador.convertirADTOM(sCita.buscarMiMedico(nSS));
		} catch (ExcepcionServicio ex) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage());
		}
	}

	@GetMapping("/{id}/buscarXId")
	public CitaDTO buscarXId(@PathVariable("id") int id) {
		try {
			return transformador.convertirADTOC(sCita.buscarXId(id));
		} catch (ExcepcionServicio ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
		}
	}
}
