/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.controladores;

import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto.config.JwtToken;
import com.proyecto.dto.jwt.JwtResponse;
import com.proyecto.modelos.Medico;
import com.proyecto.modelos.Paciente;
import com.proyecto.servicios.ServiciosJwtUsuarios;
import com.proyecto.servicios.ServiciosMedico;
import com.proyecto.servicios.ServiciosPaciente;

/**
 *
 * @author ck
 */
@RestController
@CrossOrigin
@RequestMapping("/autenticacion")
public class AutenticacionController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtToken jwtToken;

	@Autowired
	private ServiciosJwtUsuarios jwtUserDetailsService;

	@Autowired
	private ServiciosMedico sMedico;

	@Autowired
	private ServiciosPaciente sPaciente;

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody Map<String, String> request) throws Exception {
		String identificador = request.get("identificador").toLowerCase();
		String password = request.get("password");
		authenticate(identificador, password);
		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(identificador);
		String token = "";
		if (identificador.toUpperCase().startsWith("M")) {
			Optional<Medico> optMedico = sMedico.buscarMedico(identificador);
			if (optMedico.isPresent()) {
				token = jwtToken.generarToken(userDetails, optMedico.get());
			}
		} else if (identificador.toUpperCase().startsWith("ES")) {
			Optional<Paciente> optPaciente = sPaciente.buscarPaciente(identificador);
			if (optPaciente.isPresent()) {
				token = jwtToken.generarToken(userDetails, optPaciente.get());
			}
		}
		if (token.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
		}
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@Transactional
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credenciales invalidas");
		}
	}
}