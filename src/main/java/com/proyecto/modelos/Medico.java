/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

/**
 *
 * @author ck
 */
@Entity
@Table(name = "medico")
public class Medico implements Serializable, Logable {

    private static final long serialVersionUID = 3L;
    @Id
    @Size(max = 12)
    @Basic(optional = false)
    @Column(name = "n_licencia")
    private String nLicencia;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "especialidad")
    private String especialidad;
    @Basic(optional = false)
    @Column(name = "consulta")
    private int consulta;
    @Column(name = "password", nullable = false)
    private String password;
    @JoinColumn(name = "nombre_hos")
    @ManyToOne(optional = true)
    private Hospital hospital;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medico")
    private List<Cita> listaCitas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medico")
    private List<Informe> listaInformes;

    public Medico() {
        this.listaInformes = new ArrayList<>();
        this.listaCitas = new ArrayList<>();
    }

    public Medico(String nLicencia, String nombre, String especialidad, int consulta, String password, Hospital hospital, List<Cita> listaCitas, List<Informe> listaInformes) {
        this.nLicencia = nLicencia;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.consulta = consulta;
        this.password = password;
        this.hospital = hospital;
        this.listaCitas = listaCitas;
        this.listaInformes = listaInformes;
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

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public List<Cita> getListaCitas() {
        return listaCitas;
    }

    public void setListaCitas(List<Cita> listaCitas) {
        this.listaCitas = listaCitas;
    }

    public List<Informe> getListaInformes() {
        return listaInformes;
    }

    public void setListaInformes(List<Informe> listaInformes) {
        this.listaInformes = listaInformes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getIdentifier() {
        return getnLicencia();
    }

    @Override
    public String toString() {
        return "Medico{" + "nLicencia=" + nLicencia + ", nombre=" + nombre + ", especialidad=" + especialidad + ", consulta=" + consulta + ", password=" + password + ", hospital=" + hospital + ", listaCitas=" + listaCitas + ", listaInformes=" + listaInformes + '}';
    }

}
