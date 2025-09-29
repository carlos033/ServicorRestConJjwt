/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.domain.dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ck
 */
public record HospitalDTO(long id, String nombreHos, String poblacion, String numConsultas,
    List<MedicoDTO> listaMedicos) implements Serializable {

}
