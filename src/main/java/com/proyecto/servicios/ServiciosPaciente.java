/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Paciente;
import com.proyecto.repositorios.PacienteRepository;
import com.proyecto.serviciosI.ServiciosPacienteI;

import jakarta.transaction.Transactional;
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
	public Optional<Paciente> buscarPaciente(String nSS) throws ExcepcionServicio {
		return Optional.ofNullable(
				repositorioP.findById(nSS).orElseThrow(() -> new ExcepcionServicio("El numero de SS no existe")));
	}
}
