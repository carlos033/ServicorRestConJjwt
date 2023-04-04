/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ck
 */
public class HospitalDTO implements Serializable {

    private static final long serialVersionUID = 7L;
    private String nombreHos;
    private String poblacion;
    private String numConsultas;
    private List<MedicoDTO> listaMedicos;

    public HospitalDTO() {
        listaMedicos = new ArrayList<>();
    }

    public HospitalDTO(String nombreHos, String poblacion, List<MedicoDTO> listaMedicos, String numConsultas) {
        this.nombreHos = nombreHos;
        this.poblacion = poblacion;
        this.listaMedicos = listaMedicos;
        this.numConsultas = numConsultas;
    }

    public String getNumConsultas() {
        return numConsultas;
    }

    public void setNumConsultas(String numConsultas) {
        this.numConsultas = numConsultas;
    }

    public String getNombreHos() {
        return nombreHos;
    }

    public void setNombreHos(String nombreHos) {
        this.nombreHos = nombreHos;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public List<MedicoDTO> getListaMedicos() {
        return listaMedicos;
    }

    public void setListaMedicos(List<MedicoDTO> listaMedicos) {
        this.listaMedicos = listaMedicos;
    }

    @Override
    public String toString() {
        return "HospitalDTO{" + "nombreHos=" + nombreHos + ", poblacion=" + poblacion + ", listaMedicos=" + listaMedicos + ", numConsultas=" + numConsultas + '}';
    }
}
