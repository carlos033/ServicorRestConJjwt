/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.domain.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author ck
 */
public record CitaDTO(long id, LocalDateTime fechaCita, PacienteDTO paciente, MedicoDTO medico) implements Serializable {
}
