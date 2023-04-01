/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.dto.jwt;

import java.io.Serializable;

/**
 *
 * @author ck
 */
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = 12L;
    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getJwttoken() {
        return this.jwttoken;
    }

}
