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
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 11L;
    private String identificador;
    private String password;

    public JwtRequest() {
    }

    public JwtRequest(String identificador, String password) {
        this.setIdentificador(identificador);
        this.setPassword(password);
    }

    public String getIdentificador() {
        return this.identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
