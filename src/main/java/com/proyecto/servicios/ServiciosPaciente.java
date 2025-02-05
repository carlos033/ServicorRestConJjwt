/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.proyecto.servicios;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Paciente;
import com.proyecto.repositorios.PacienteRepository;
import com.proyecto.serviciosI.ServiciosPacienteI;

import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@Service
@Transactional
public class ServiciosPaciente implements ServiciosPacienteI {

	private PacienteRepository repositorioP;

	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public List<Paciente> buscarTodosP() {
		return repositorioP.findAll();
	}

	@Override
	public void eliminarPaciente(String nSS) throws ExcepcionServicio {
		buscarPaciente(nSS);
		repositorioP.deleteById(nSS);
	}

	@Override
	public void savePaciente(Paciente paciente1) {
		paciente1.setPassword(passwordEncoder.encode(paciente1.getPassword()));
		repositorioP.save(paciente1);
	}

	@Override
	@Transactional(readOnly = true)
	public Paciente buscarPaciente(String nSS) throws ExcepcionServicio {
		return repositorioP.findById(nSS).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El numero de SS no existe"));
	}
}
