/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.modelos;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author ck
 */
@Entity
@Table(name = "informes")
public class Informe implements Serializable {

    private static final long serialVersionUID = 2L;
    @Id
    @Basic(optional = false)
    @Column(name = "nombre_inf")
    private String nombreInf;
    @Basic(optional = false)
    @Column(name = "url", unique = true)
    private String url;
    @JoinColumn(name = "nss", referencedColumnName = "nss")
    @ManyToOne(optional = false)
    private Paciente paciente;
    @JoinColumn(name = "n_licencia", referencedColumnName = "n_licencia")
    @ManyToOne(optional = false)
    private Medico medico;

    public Informe() {
        this.medico = new Medico();
        this.paciente = new Paciente();
    }

    public Informe(String nombreInf, String url, Paciente paciente, Medico medico) {
        this.nombreInf = nombreInf;
        this.url = url;
        this.paciente = paciente;
        this.medico = medico;
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

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    @Override
    public String toString() {
        return "Informe{" + "nombreInf=" + nombreInf + ", url=" + url
                + ", paciente=" + paciente + ", medico=" + medico + '}';
    }

}
