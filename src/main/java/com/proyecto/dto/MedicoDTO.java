/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ck
 */
public class MedicoDTO implements Serializable {

    private static final long serialVersionUID = 9L;
    private String nLicencia;
    private String nombre;
    private String especialidad;
    private int consulta;
    private String password;
    @JsonIgnoreProperties("listaMedicos")
    private HospitalDTO hospital;
    private List<CitaDTO> listaCitas;
    private List<InformeDTO> listaInformes;

    public MedicoDTO() {
        this.listaInformes = new ArrayList<>();
        this.listaCitas = new ArrayList<>();
    }

    public MedicoDTO(String nLicencia, String nombre, String especialidad, int consulta, String password, HospitalDTO hospital, List<CitaDTO> listaCitas, List<InformeDTO> listaInformes) {
        this.nLicencia = nLicencia;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.consulta = consulta;
        this.password = password;
        this.hospital = hospital;
        this.listaCitas = listaCitas;
        this.listaInformes = listaInformes;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getnLicencia() {
        return nLicencia;
    }

    public void setnLicencia(String nLicencia) {
        this.nLicencia = nLicencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getConsulta() {
        return consulta;
    }

    public void setConsulta(int consulta) {
        this.consulta = consulta;
    }

    public HospitalDTO getHospital() {
        return hospital;
    }

    public void setHospital(HospitalDTO hospital) {
        this.hospital = hospital;
    }

    public List<CitaDTO> getListaCitas() {
        return listaCitas;
    }

    public void setListaCitas(List<CitaDTO> listaCitas) {
        this.listaCitas = listaCitas;
    }

    @JsonIgnore
    public List<InformeDTO> getListaInformes() {
        return listaInformes;
    }

    public void setListaInformes(List<InformeDTO> listaInformes) {
        this.listaInformes = listaInformes;
    }

    @Override
    public String toString() {
        return "MedicoDTO{" + "nLicencia=" + nLicencia + ", nombre=" + nombre + ", especialidad=" + especialidad + ", consulta=" + consulta + ", hospital=" + hospital + ", listaCitas=" + listaCitas + ", listaInformes=" + listaInformes + '}';
    }
}
