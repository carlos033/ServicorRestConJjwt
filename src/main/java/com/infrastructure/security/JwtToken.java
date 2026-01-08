package com.infrastructure.security;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.domain.model.Logable;
import com.domain.model.Medico;
import com.domain.model.Paciente;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtToken implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final Duration JWT_TOKEN_VALIDITY = Duration.ofHours(30);

  private SecretKey secretKey;

  public JwtToken(@Value("${security.jwt.secret}") String secret) {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  public SecretKey getSecretKey() {
    return secretKey;
  }

  public String obtenerIdentificadorDelToken(String token) {
    return getAllClaimsFromToken(token).getSubject();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    return (obtenerIdentificadorDelToken(token).equals(userDetails.getUsername())
        && !aExpiradoElToken(token));
  }

  public Date ObtenerVencimientoDelToken(String token) {
    return getAllClaimsFromToken(token).getExpiration();
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
    Instant now = Instant.now();

    // Construcción del token
    JwtBuilder builder = Jwts.builder().claim("sub", subject) // subject
        .claim("iss", "exampleIssuer") // issuer
        .claim("aud", "exampleAudience") // audience
        .claim("iat", now.getEpochSecond()) // issued at
        .claim("exp", now.plus(JWT_TOKEN_VALIDITY).getEpochSecond()) // expiration
        .signWith(getSecretKey()); // clave ya contiene algoritmo

    // Añadimos claims personalizados uno a uno
    userClaims.forEach(builder::claim);

    return builder.compact();
  }

}
