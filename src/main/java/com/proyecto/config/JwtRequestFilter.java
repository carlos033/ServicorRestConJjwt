/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.config;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.proyecto.servicios.ServiciosJwtUsuarios;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private JwtToken jwtTokenUtil;
	private ApplicationContext applicationContext;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			String jwtToken = requestTokenHeader.substring(7);
			try {
				String identifier = jwtTokenUtil.obtenerIdentificadorDelToken(jwtToken);
				if (identifier != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					ServiciosJwtUsuarios jwtUserDetailsService = applicationContext.getBean(ServiciosJwtUsuarios.class);
					UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(identifier);

					SecurityContextHolder.getContext()
							.setAuthentication(jwtTokenUtil.validateToken(jwtToken, userDetails)
									? buildAuthenticationToken(userDetails, request)
									: null);
				}
			} catch (JwtException exception) {
				logger.error("No se pudo obtener el token JWT o el token JWT ha expirado", exception);
			}
		}
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken buildAuthenticationToken(UserDetails userDetails,
			HttpServletRequest request) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
				null, userDetails.getAuthorities());

		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		return authenticationToken;
	}
}
