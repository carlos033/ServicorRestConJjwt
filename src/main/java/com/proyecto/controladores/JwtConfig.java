package com.proyecto.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.proyecto.config.JwtRequestFilter;
@Configuration
public class JwtConfig {
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

}
