/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.modelos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author ck
 */
@Entity
@Table(
        name = "cita",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"n_licencia", "f_hora_cita"}, name = "UK_medico_cita"),
            @UniqueConstraint(columnNames = {"nss", "f_hora_cita"}, name = "UK_paciente_cita")
        }
)
public class Cita implements Serializable {

    private static final long serialVersionUID = 5L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private int id;
    @Basic(optional = false)
    @Column(name = "f_hora_cita")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fHoraCita;
    @JoinColumn(name = "nss", referencedColumnName = "nss")
    @ManyToOne(optional = false)
    private Paciente paciente;
    @JoinColumn(name = "n_licencia", referencedColumnName = "n_licencia")
    @ManyToOne(optional = false)
    private Medico medico;

    public Cita() {
    }

    public Cita(Date fHoraCita, Paciente paciente, Medico medico) {
        this.fHoraCita = fHoraCita;
        this.paciente = paciente;
        this.medico = medico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getfHoraCita() {
        return fHoraCita;
    }

    public void setfHoraCita(Date fHoraCita) {
        this.fHoraCita = fHoraCita;
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
        return "Cita{" + "id=" + id + ", fHoraCita=" + fHoraCita + ", paciente=" + paciente + ", medico=" + medico + '}';
    }
}
