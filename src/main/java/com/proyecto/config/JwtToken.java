package com.proyecto.config;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.proyecto.modelos.Logable;
import com.proyecto.modelos.Medico;
import com.proyecto.modelos.Paciente;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtToken implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final long JWT_TOKEN_VALIDITY = 30 * 60 * 60;

	private SecretKey secretKey;

	@PostConstruct
	public void init() throws Exception {
		this.secretKey = Jwts.SIG.HS256.key().build();
	}

	public SecretKey getSecretKey() {
		return secretKey;
	}

	public String obtenerIdentificadorDelToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date ObtenerVencimientoDelToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		return claimsResolver.apply(getAllClaimsFromToken(token));
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
	}

	private Boolean aExpiradoElToken(String token) {
		final Date expiration = ObtenerVencimientoDelToken(token);
		return expiration.before(new Date());
	}

	public String generarToken(UserDetails userDetails, Logable user) {
		Map<String, Object> claims = new HashMap<>();
		Map<String, Object> userMap = new HashMap<>();
		if (user instanceof Medico medico) {
			userMap.put("licencia", user.getIdentifier());
			userMap.put("nombre", medico.getNombre());
		} else if (user instanceof Paciente paciente) {
			userMap.put("nss", user.getIdentifier());
			userMap.put("nombre", paciente.getNombre());
		}
		claims.put("usuario", userMap);
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> userClaims, String subject) {
		SecretKey signingKey = Keys.hmacShaKeyFor(getSecretKey().getEncoded());

		JwtBuilder builder = Jwts.builder().audience().add("exampleAudience").and().issuer("exampleIssuer").subject(subject).issuedAt(Date.from(Instant.now())).expiration(Date.from(Instant.now().plusSeconds(JWT_TOKEN_VALIDITY))).signWith(signingKey);

		userClaims.entrySet().forEach(entry -> builder.claim(entry.getKey(), entry.getValue()));

		return builder.compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		return (obtenerIdentificadorDelToken(token).equals(userDetails.getUsername()) && !aExpiradoElToken(token));
	}
}
