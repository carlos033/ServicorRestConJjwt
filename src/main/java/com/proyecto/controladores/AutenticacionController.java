/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.controladores;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/autenticacion")
public class AutenticacionController {

	private AuthenticationManager authenticationManager;

	private JwtToken jwtToken;

	private ServiciosJwtUsuarios jwtUserDetailsService;

	private ServiciosMedico sMedico;

	private ServiciosPaciente sPaciente;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody Map<String, String> request)
			throws Exception {
		String identificador = request.get("identificador").toLowerCase();
		String password = request.get("password");
		authenticate(identificador, password);
		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(identificador);

		String token = "";

		if (identificador.toUpperCase().startsWith("M")) {
			Optional<Medico> optMedico = sMedico.buscarMedico(identificador);
			if (optMedico.isPresent()) {
				token = jwtToken.generarToken(userDetails, optMedico.get());
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
			}
		} else if (identificador.toUpperCase().startsWith("ES")) {
			Optional<Paciente> optPaciente = sPaciente.buscarPaciente(identificador);
			if (optPaciente.isPresent()) {
				token = jwtToken.generarToken(userDetails, optPaciente.get());
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
			}
		} else {
			throwInvalidIdentifierException();
		}

		return ResponseEntity.ok(new JwtResponse(token));
	}

	private String throwInvalidIdentifierException() {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Identificador no válido");
	}

	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(CsrfConfigurer::disable)
				.authorizeHttpRequests(requests -> requests.requestMatchers("/autenticacion/**").permitAll()
						.anyRequest().authenticated())
				.exceptionHandling(withDefaults())
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	}

	@Transactional
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credenciales inválidas");
		}
	}
}