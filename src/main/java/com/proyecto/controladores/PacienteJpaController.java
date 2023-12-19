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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto.dto.CitaDTO;
import com.proyecto.dto.InformePacienteDTO;
import com.proyecto.dto.PacienteDTO;
import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Cita;
import com.proyecto.serviciosI.ServiciosCitaI;
import com.proyecto.serviciosI.ServiciosInformeI;
import com.proyecto.serviciosI.ServiciosPacienteI;
import com.proyecto.utiles.Transformadores;

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

	private Transformadores transformador;

	private ServiciosPacienteI sPaciente;

	private ServiciosCitaI sCita;

	private ServiciosInformeI sInformes;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PacienteDTO aniadirPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
		sPaciente.savePaciente(transformador.convertirAEntidadP(pacienteDTO));
		return pacienteDTO;
	}

	@DeleteMapping("/{nSS}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminarPaciente(@PathVariable("nSS") String nSS) {
		try {
			sCita.eliminarTodasXPaciente(nSS);
			sPaciente.eliminarPaciente(nSS);
		} catch (ExcepcionServicio ex) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage());
		}
	}

	@GetMapping
	public List<PacienteDTO> listPacientes() {
		return sPaciente.buscarTodosP().stream().map(transformador::convertirADTOP).collect(Collectors.toList());
	}

	@GetMapping("/{nSS}")
	public PacienteDTO buscarPaciente(@RequestParam String nSS) {
		try {
			return sPaciente.buscarPaciente(nSS).map(transformador::convertirADTOP)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Paciente no encontrado"));
		} catch (ExcepcionServicio ex) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage());
		}
	}

	@GetMapping("/{nSS}/citas")
	public List<CitaDTO> buscarCitasPaciente(@PathVariable("nSS") String nSS) {
		try {
			List<Cita> citas = sCita.buscarXPaciente(nSS);
			return citas.stream().map(transformador::convertirADTOC).collect(Collectors.toList());
		} catch (ExcepcionServicio ex) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage());
		}
	}

	@GetMapping("/{nSS}/informes")
	public List<InformePacienteDTO> buscarInformesXPaciente(@PathVariable("nSS") String nSS) {
		try {
			return sInformes.buscarInformesXPaciente(nSS).stream().map(transformador::convertirADTOIP)
					.collect(Collectors.toList());
		} catch (ExcepcionServicio ex) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage());
		}
	}

}
