/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.web.controller;

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

import com.application.service.impl.ServiciosJwtUsuarios;
import com.application.service.impl.ServiciosMedico;
import com.application.service.impl.ServiciosPaciente;
import com.domain.dto.jwt.JwtResponse;
import com.domain.dto.jwt.LoginRequest;
import com.infrastructure.security.JwtToken;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/autenticacion")
public class AutenticacionController {

	private final AuthenticationManager authenticationManager;

	private final JwtToken jwtToken;

	private final ServiciosJwtUsuarios jwtUserDetailsService;

	private final ServiciosMedico sMedico;

	private final ServiciosPaciente sPaciente;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody LoginRequest request) throws Exception {
		String identificador = request.identificador().toLowerCase();
		String password = request.password();
		authenticate(identificador, password);
		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(identificador);

		String token = generateToken(identificador, userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	private String generateToken(String identificador, UserDetails userDetails) {
		if (identificador.toUpperCase().startsWith("M")) {
			return jwtToken.generarToken(userDetails, sMedico.buscarMedico(identificador));
		} else if (identificador.toUpperCase().startsWith("ES")) {
			return jwtToken.generarToken(userDetails, sPaciente.buscarPaciente(identificador));
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Identificador no válido");
		}
	}

	@Transactional
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario deshabilitado");
		} catch (BadCredentialsException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credenciales inválidas");
		}
	}
}