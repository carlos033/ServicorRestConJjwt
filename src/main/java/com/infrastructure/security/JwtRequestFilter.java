/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.infrastructure.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.application.service.impl.ServiciosJwtUsuarios;

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

	private final JwtToken jwtTokenUtil;
	private final ServiciosJwtUsuarios jwtUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		final Optional<String> optAuthHeader = Optional.ofNullable(request.getHeader("Authorization"));
		if (optAuthHeader.isPresent() && optAuthHeader.get().startsWith("Bearer ")) {
			String jwtToken = optAuthHeader.get().substring(7);
			try {
				String identifier = jwtTokenUtil.obtenerIdentificadorDelToken(jwtToken);
				if (identifier != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(identifier);
					UsernamePasswordAuthenticationToken authToken = buildAuthenticationToken(userDetails, request);
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			} catch (JwtException exception) {
				logger.error("No se pudo obtener el token JWT o el token JWT ha expirado", exception);
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				response.getWriter().write("{\"error\": \"Token inválido o expirado\"}");
			}
		}
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken buildAuthenticationToken(UserDetails userDetails, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		return authenticationToken;
	}
}
