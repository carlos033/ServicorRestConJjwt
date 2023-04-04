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
public class InformeMedicoDTO extends InformeDTO {

	private static final long serialVersionUID = 4395919023616974988L;
	@JsonIgnoreProperties("informes")
    private PacienteDTO paciente;

    public InformeMedicoDTO() {
    }

    public InformeMedicoDTO(PacienteDTO paciente, String url, String nombreInf) {
        super(url, nombreInf);
        this.paciente = paciente;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
    }

    @Override
    public String toString() {
        return "InformeMedicoDTO{" + "paciente=" + paciente + '}';
    }

}
