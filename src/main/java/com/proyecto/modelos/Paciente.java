/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ck
 */
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable, Logable {

    private static final long serialVersionUID = 4L;
    @Id
    @Basic(optional = false)
    @Column(name = "nss")
    private String nSS;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "f_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fNacimiento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente")
    private List<Cita> listaCitas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente")
    private List<Informe> informes;

    public Paciente() {
        this.informes = new ArrayList<>();
        this.listaCitas = new ArrayList<>();
    }

    public Paciente(String nSS, String nombre, String password, Date fNacimiento, List<Cita> listaCitas, List<Informe> informes) {
        this.nSS = nSS;
        this.nombre = nombre;
        this.password = password;
        this.fNacimiento = fNacimiento;
        this.listaCitas = listaCitas;
        this.informes = informes;
    }

    public String getnSS() {
        return nSS;
    }

    public void setnSS(String nSS) {
        this.nSS = nSS;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getfNacimiento() {
        return fNacimiento;
    }

    public void setfNacimiento(Date fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    public List<Cita> getListaCitas() {
        return listaCitas;
    }

    public void setListaCitas(List<Cita> listaCitas) {
        this.listaCitas = listaCitas;
    }

    public List<Informe> getInformes() {
        return informes;
    }

    public void setInformes(List<Informe> informes) {
        this.informes = informes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getIdentifier() {
        return getnSS();
    }

    @Override
    public String toString() {
        return "Paciente{" + "nSS=" + nSS + ", nombre=" + nombre + ", password=" + password + ", fNacimiento=" + fNacimiento + ", citas=" + listaCitas + ", informes=" + informes + '}';
    }
}
