/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.dto;

/**
 *
 * @author ck
 */
public class InformeCompletoDTO {

    private String url;
    private String nombreInf;
    private MedicoDTO medico;
    private PacienteDTO paciente;

    public InformeCompletoDTO(String url, String nombreInf, MedicoDTO medico, PacienteDTO paciente) {
        this.url = url;
        this.nombreInf = nombreInf;
        this.medico = medico;
        this.paciente = paciente;
    }

    public InformeCompletoDTO() {
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

    public MedicoDTO getMedico() {
        return medico;
    }

    public void setMedico(MedicoDTO medico) {
        this.medico = medico;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
    }

    @Override
    public String toString() {
        return "InforneCompletoDTO{" + "url=" + url + ", nombreInf=" + nombreInf + ", medico=" + medico + ", paciente=" + paciente + '}';
    }

}
