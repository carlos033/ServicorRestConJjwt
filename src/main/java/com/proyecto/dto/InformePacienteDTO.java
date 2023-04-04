/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author ck
 */
public class InformePacienteDTO extends InformeDTO {

	private static final long serialVersionUID = -4876410190204802920L;
	@JsonIgnoreProperties("listaInformes")
    private MedicoDTO medico;

    public InformePacienteDTO() {
    }

    public InformePacienteDTO(MedicoDTO medico, String url, String nombreInf) {
        super(url, nombreInf);
        this.medico = medico;
    }

    public MedicoDTO getMedico() {
        return medico;
    }

    public void setMedico(MedicoDTO medico) {
        this.medico = medico;
    }

    @Override
    public String toString() {
        return "InformePacienteDTO{" + "medico=" + medico + '}';
    }
}
