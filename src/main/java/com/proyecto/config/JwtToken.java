/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.config;

import com.proyecto.modelos.Logable;
import com.proyecto.modelos.Medico;
import com.proyecto.modelos.Paciente;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author ck
 */
@Component
public class JwtToken implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 30 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    public String obtenerIdentificadorDelToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date ObtenerVencimientoDelToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean aExpiradoElToken(String token) {
        final Date expiration = ObtenerVencimientoDelToken(token);
        return expiration.before(new Date());
    }

    public boolean esMedico(String token) {
        Claims claims = getAllClaimsFromToken(token);
        Map<String, Object> userMap = (HashMap<String, Object>) claims.get("usuario");
        return userMap.get("licencia") != null;
    }

    public String generarToken(UserDetails userDetails, Logable user) {
        Map<String, Object> claims = new HashMap<>();
        Map<String, Object> userMap = new HashMap<>();
        Medico medico = null;
        Paciente paciente = null;
        if (user instanceof Medico) {
            userMap.put("licencia", user.getIdentifier());
            medico = (Medico) user;
            userMap.put("nombre", medico.getNombre());
        } else if (user instanceof Paciente) {
            userMap.put("nss", user.getIdentifier());
            paciente = (Paciente) user;
            userMap.put("nombre", paciente.getNombre());
        }
        claims.put("usuario", userMap);
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String identifier = obtenerIdentificadorDelToken(token);
        return (identifier.equals(userDetails.getUsername()) && !aExpiradoElToken(token));
    }

}
