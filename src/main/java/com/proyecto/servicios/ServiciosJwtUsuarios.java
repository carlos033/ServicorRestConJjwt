/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.proyecto.modelos.Medico;
import com.proyecto.modelos.Paciente;
import com.proyecto.repositorios.MedicoRepository;
import com.proyecto.repositorios.PacienteRepository;
import com.proyecto.serviciosI.ServicioJwtUsuario;

import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@Service
public class ServiciosJwtUsuarios implements ServicioJwtUsuario {

	private MedicoRepository medicoRepository;

	private PacienteRepository pacienteRepository;

	private UserDetails cargarMedicoPorIdentificador(String identificador) {
		Optional<Medico> optMedico = medicoRepository.findById(identificador);
		if (!optMedico.isPresent()) {
			throw new UsernameNotFoundException("Usuario no encontrado con identificador: " + identificador);
		}
		Medico m = optMedico.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		return new org.springframework.security.core.userdetails.User(m.getnLicencia(), m.getPassword(), authorities);
	}

	private UserDetails cargarPacientePorIdentificador(String identificador) {
		Optional<Paciente> optPaciente = pacienteRepository.findById(identificador);
		Paciente p = optPaciente.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		return new org.springframework.security.core.userdetails.User(p.getnSS(), p.getPassword(), authorities);
	}

	@Override
	public UserDetails loadUserByUsername(String identificador) {
		if (identificador.startsWith("m")) {
			return cargarMedicoPorIdentificador(identificador);
		} else if (identificador.startsWith("es")) {
			return cargarPacientePorIdentificador(identificador);
		}
		return null;
	}
}
