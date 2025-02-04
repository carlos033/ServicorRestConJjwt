/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.modelos.Paciente;

/**
 *
 * @author ck
 */

public interface PacienteRepository extends JpaRepository<Paciente, String> {
}
