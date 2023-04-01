/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.dto;

import java.io.Serializable;

/**
 *
 * @author ck
 */
public class InformeDTO implements Serializable {

    private static final long serialVersionUID = 8L;
    protected String url;
    protected String nombreInf;

    public InformeDTO() {

    }

    public InformeDTO(String url, String nombreInf) {
        this.url = url;
        this.nombreInf = nombreInf;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombreInf() {
        return nombreInf;
    }

    public void setNombreInf(String nombreInf) {
        this.nombreInf = nombreInf;
    }

    @Override
    public String toString() {
        return "InformeDTO{url=" + url + ", nombreInf=" + nombreInf + '}';
    }
}
