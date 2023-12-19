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
import com.proyecto.dto.InformeMedicoDTO;
import com.proyecto.dto.MedicoDTO;
import com.proyecto.dto.PacienteDTO;
import com.proyecto.excepciones.ExcepcionServicio;
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

	private Transformadores transformador;

	private ServiciosMedicoI sMedico;

	private ServiciosCitaI sCita;

	private ServiciosInformeI sInformes;

	@PostMapping

	@ResponseStatus(HttpStatus.CREATED)
	public MedicoDTO aniadirMedico(@Valid @RequestBody MedicoDTO medicoDTO) {
		Medico medico = transformador.convertirAEntidadM(medicoDTO);
		sMedico.saveMedico(medico);
		return transformador.convertirADTOM(medico);
	}

	@DeleteMapping("/{nLicencia}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminarMedico(@PathVariable("nLicencia") String nLicencia) {
		try {
			sMedico.eliminarMedico(nLicencia);
		} catch (ExcepcionServicio ex) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage());
		}
	}

	@GetMapping("/{nLicencia}")
	public MedicoDTO buscarMedico(@PathVariable("nLicencia") String nLicencia) {
		try {
			return sMedico.buscarMedico(nLicencia).map(transformador::convertirADTOM)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Médico no encontrado"));
		} catch (ExcepcionServicio ex) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage());
		}
	}

	@GetMapping
	public List<MedicoDTO> listMedicos() {
		return sMedico.buscarTodosM().stream().map(transformador::convertirADTOM).collect(Collectors.toList());
	}

	@GetMapping("/{nLicencia}/citas")
	public List<CitaDTO> buscarCitaXMedico(@PathVariable("nLicencia") String nLicencia) {
		try {
			return sCita.buscarXMedico(nLicencia).stream().map(transformador::convertirADTOC)
					.collect(Collectors.toList());
		} catch (ExcepcionServicio ex) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage());
		}
	}

	@GetMapping("/{nLicencia}/pacientes")
	public List<PacienteDTO> buscarPacienteXMedico(@PathVariable("nLicencia") String nLicencia) {
		try {
			return sMedico.BuscarPacientesXMedico(nLicencia).stream().map(transformador::convertirADTOP)
					.collect(Collectors.toList());
		} catch (ExcepcionServicio ex) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage());
		}
	}

	@GetMapping("/{especialidad}/{nombrehos}/hospital")
	public List<MedicoDTO> BuscarMedicoXEspecialidad(@PathVariable("especialidad") String especialidad,
			@PathVariable("nombrehos") String nombrehos) {
		try {
			return sMedico.BuscarMedicoXEspecialidad(especialidad, nombrehos).stream()
					.map(transformador::convertirADTOM).collect(Collectors.toList());
		} catch (ExcepcionServicio ex) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage());
		}
	}

	@GetMapping("/{nombrehos}/hospital")
	public List<MedicoDTO> BuscarMedicosXHospital(@PathVariable("nombrehos") String nombrehos) {
		try {
			return sMedico.BuscarMedicosXHospital(nombrehos).stream().map(transformador::convertirADTOM)
					.collect(Collectors.toList());
		} catch (ExcepcionServicio ex) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage());
		}
	}

	@GetMapping("/{nLicencia}/informes")
	public List<InformeMedicoDTO> buscarInformesXPaciente(@PathVariable("nLicencia") String nLicencia) {
		try {
			return sInformes.buscarInformesXMedico(nLicencia).stream().map(transformador::convertirADTOIM)
					.collect(Collectors.toList());
		} catch (ExcepcionServicio ex) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage());
		}
	}
}
