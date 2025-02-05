/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.proyecto.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author ck
 */
public record PacienteDTO(String nSS, String nombre, String password, LocalDate fNacimiento, List<CitaDTO> citas, List<InformeDTO> informes) implements Serializable {

}
