/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.proyecto.servicios;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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

	private final MedicoRepository medicoRepository;

	private final PacienteRepository pacienteRepository;

	private UserDetails crearUsuario(String identificador, String password, String rol) {
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(rol));
		return new User(identificador, password, authorities);
	}

	private UserDetails cargarMedicoPorIdentificador(String identificador) {
		Medico medico = medicoRepository.findById(identificador).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con identificador: " + identificador)); // 🔹 Código más limpio
		return crearUsuario(medico.getnLicencia(), medico.getPassword(), "ROLE_MEDICO");
	}

	private UserDetails cargarPacientePorIdentificador(String identificador) {
		Paciente paciente = pacienteRepository.findById(identificador).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con identificador: " + identificador)); // 🔹 Código más limpio
		return crearUsuario(paciente.getNSS(), paciente.getPassword(), "ROLE_PACIENTE");

	}

	@Override
	public UserDetails loadUserByUsername(String identificador) {
		return switch (identificador) {
		case String s when s.startsWith("m") -> cargarMedicoPorIdentificador(s);
		case String s when s.startsWith("es") -> cargarPacientePorIdentificador(s);
		default -> throw new UsernameNotFoundException("Identificador no válido");
		};
	}
}
