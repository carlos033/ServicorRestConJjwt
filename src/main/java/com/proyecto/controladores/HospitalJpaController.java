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
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.dto.HospitalDTO;
import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Hospital;
import com.proyecto.serviciosI.ServiciosHospitalI;
import com.proyecto.utiles.Transformadores;

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

	private Transformadores transformador;

	private ServiciosHospitalI sHospital;

	@PostMapping
	public ResponseEntity<HospitalDTO> aniadirHospital(@Valid @RequestBody HospitalDTO hospitalDto) {
		Hospital hospital1 = transformador.convertirAEntidadH(hospitalDto);
		sHospital.save(hospital1);
		return ResponseEntity.status(HttpStatus.CREATED).body(transformador.convertirADTOH(hospital1));
	}

	@DeleteMapping("/{nombre}")
	public ResponseEntity<Void> eliminarHospital(@PathVariable String nombre) throws ExcepcionServicio {
		sHospital.eliminarHospital(nombre);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<HospitalDTO>> listarhospitales() {
		return ResponseEntity.ok(sHospital.buscarTodosH().stream().map(transformador::convertirADTOH).collect(Collectors.toList()));
	}

	@GetMapping("{nombre}")
	public ResponseEntity<HospitalDTO> buscarHospital(@PathVariable String nombre) throws ExcepcionServicio {
		return ResponseEntity.ok(transformador.convertirADTOH(sHospital.buscarHospital(nombre)));
	}
}
