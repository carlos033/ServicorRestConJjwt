/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
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
	@ResponseStatus(HttpStatus.CREATED)
	public HospitalDTO aniadirHospital(@Valid @RequestBody HospitalDTO hospitalDto) {
		Hospital hospital1 = transformador.convertirAEntidadH(hospitalDto);
		sHospital.save(hospital1);
		return transformador.convertirADTOH(hospital1);
	}

	@DeleteMapping("/{nombre}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminarHospital(@PathVariable String nombre) throws ExcepcionServicio {
		sHospital.eliminarHospital(nombre);
	}

	@GetMapping
	public List<HospitalDTO> listarhospitales() {
		return sHospital.buscarTodosH().stream().map(transformador::convertirADTOH).collect(Collectors.toList());
	}

	@GetMapping("{nombre}")
	public HospitalDTO buscarHospital(@PathVariable String nombre) throws ExcepcionServicio {
		return transformador.convertirADTOH(sHospital.buscarHospital(nombre));
	}
}
