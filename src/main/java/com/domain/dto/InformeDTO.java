/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.domain.dto;

import java.io.Serializable;

/**
 *
 * @author ck
 */
public record InformeDTO(long id, String url, String nombreInf, MedicoDTO medico, PacienteDTO paciente) implements Serializable {

}
