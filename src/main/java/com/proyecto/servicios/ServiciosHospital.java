/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.proyecto.servicios;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Hospital;
import com.proyecto.repositorios.HospitalRepository;
import com.proyecto.serviciosI.ServiciosHospitalI;

import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@Service
@Transactional
public class ServiciosHospital implements ServiciosHospitalI {

	private HospitalRepository repositorioH;

	@Override
	public List<Hospital> buscarTodosH() {
		return repositorioH.findAll();
	}

	@Override
	public void save(Hospital hospital1) {
		repositorioH.save(hospital1);
	}

	@Override
	public void eliminarHospital(String nombre) throws ExcepcionServicio {
		repositorioH.findById(nombre).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El hospital no existe"));

		repositorioH.deleteById(nombre);
	}

	@Override
	@Transactional(readOnly = true)
	public Hospital buscarHospital(String nombre) throws ExcepcionServicio {

		return repositorioH.findById(nombre).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El hospital no existe"));

	}
}
