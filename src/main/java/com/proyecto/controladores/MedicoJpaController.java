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

import com.proyecto.dto.CitaDTO;
import com.proyecto.dto.InformeMedicoDTO;
import com.proyecto.dto.MedicoDTO;
import com.proyecto.dto.PacienteDTO;
import com.proyecto.modelos.Medico;
import com.proyecto.serviciosI.ServiciosCitaI;
import com.proyecto.serviciosI.ServiciosInformeI;
import com.proyecto.serviciosI.ServiciosMedicoI;
import com.proyecto.utiles.Transformadores;

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

	private final Transformadores transformador;

	private final ServiciosMedicoI sMedico;

	private final ServiciosCitaI sCita;

	private final ServiciosInformeI sInformes;

	@PostMapping

	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<MedicoDTO> aniadirMedico(@Valid @RequestBody MedicoDTO medicoDTO) {
		Medico medico = transformador.convertirAEntidadM(medicoDTO);
		sMedico.saveMedico(medico);
		return ResponseEntity.status(HttpStatus.CREATED).body(transformador.convertirADTOM(medico));
	}

	@DeleteMapping("/{nLicencia}")
	public ResponseEntity<Void> eliminarMedico(@PathVariable String nLicencia) {
		sMedico.eliminarMedico(nLicencia);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{nLicencia}")
	public ResponseEntity<MedicoDTO> buscarMedico(@PathVariable String nLicencia) {
		return ResponseEntity.ok(transformador.convertirADTOM(sMedico.buscarMedico(nLicencia)));
	}

	@GetMapping
	public ResponseEntity<List<MedicoDTO>> listMedicos() {
		return ResponseEntity.ok(sMedico.buscarTodosM().stream().map(transformador::convertirADTOM).collect(Collectors.toList()));
	}

	@GetMapping("/{nLicencia}/citas")
	public ResponseEntity<List<CitaDTO>> buscarCitaXMedico(@PathVariable String nLicencia) {
		return ResponseEntity.ok(sCita.buscarXMedico(nLicencia).stream().map(transformador::convertirADTOC).collect(Collectors.toList()));

	}

	@GetMapping("/{nLicencia}/pacientes")
	public ResponseEntity<List<PacienteDTO>> buscarPacienteXMedico(@PathVariable String nLicencia) {
		return ResponseEntity.ok(sMedico.BuscarPacientesXMedico(nLicencia).stream().map(transformador::convertirADTOP).collect(Collectors.toList()));

	}

	@GetMapping("/{especialidad}/{nombrehos}/hospital")
	public ResponseEntity<List<MedicoDTO>> BuscarMedicoXEspecialidad(@PathVariable String especialidad, @PathVariable String nombrehos) {
		return ResponseEntity.ok(sMedico.BuscarMedicoXEspecialidad(especialidad, nombrehos).stream().map(transformador::convertirADTOM).collect(Collectors.toList()));

	}

	@GetMapping("/{nombrehos}/hospital")
	public ResponseEntity<List<MedicoDTO>> BuscarMedicosXHospital(@PathVariable String nombrehos) {
		return ResponseEntity.ok(sMedico.BuscarMedicosXHospital(nombrehos).stream().map(transformador::convertirADTOM).collect(Collectors.toList()));

	}

	@GetMapping("/{nLicencia}/informes")
	public ResponseEntity<List<InformeMedicoDTO>> buscarInformesXPaciente(@PathVariable String nLicencia) {
		return ResponseEntity.ok(sInformes.buscarInformesXMedico(nLicencia).stream().map(transformador::convertirADTOIM).collect(Collectors.toList()));

	}
}
