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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.dto.CitaDTO;
import com.proyecto.dto.InformePacienteDTO;
import com.proyecto.dto.PacienteDTO;
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

	private final Transformadores transformador;

	private final ServiciosPacienteI sPaciente;

	private final ServiciosCitaI sCita;

	private final ServiciosInformeI sInformes;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<PacienteDTO> aniadirPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
		sPaciente.savePaciente(transformador.convertirAEntidadP(pacienteDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(pacienteDTO);
	}

	@DeleteMapping("/{nSS}")
	public ResponseEntity<Void> eliminarPaciente(@PathVariable String nSS) {
		sCita.eliminarTodasXPaciente(nSS);
		sPaciente.eliminarPaciente(nSS);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<PacienteDTO>> listPacientes() {
		return ResponseEntity.ok(sPaciente.buscarTodosP().stream().map(transformador::convertirADTOP).collect(Collectors.toList()));
	}

	@GetMapping("/{nSS}")
	public ResponseEntity<PacienteDTO> buscarPaciente(@RequestParam String nSS) {
		return ResponseEntity.ok(transformador.convertirADTOP(sPaciente.buscarPaciente(nSS)));
	}

	@GetMapping("/{nSS}/citas")
	public ResponseEntity<List<CitaDTO>> buscarCitasPaciente(@PathVariable String nSS) {
		List<Cita> citas = sCita.buscarXPaciente(nSS);
		return ResponseEntity.ok(citas.stream().map(transformador::convertirADTOC).collect(Collectors.toList()));
	}

	@GetMapping("/{nSS}/informes")
	public ResponseEntity<List<InformePacienteDTO>> buscarInformesXPaciente(@PathVariable String nSS) {
		return ResponseEntity.ok(sInformes.buscarInformesXPaciente(nSS).stream().map(transformador::convertirADTOIP).collect(Collectors.toList()));
	}

}
