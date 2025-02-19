/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.domain.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * @author ck
 */
public record CitaDTO(long id, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm") LocalDateTime fechaCita, PacienteDTO paciente, MedicoDTO medico) implements Serializable {
}
