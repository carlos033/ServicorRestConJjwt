/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.proyecto.dto;

import java.io.Serializable;

public record MedicoDTO(String nLicencia, String nombre, String especialidad) implements Serializable {
}
