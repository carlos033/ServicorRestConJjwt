/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.serviciosI;

import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Paciente;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ck
 */
public interface ServiciosPacienteI {

    public List<Paciente> buscarTodosP();

    public void savePaciente(Paciente paciente1);

    public void eliminarPaciente(String nSS) throws ExcepcionServicio;

    public Optional<Paciente> buscarPaciente(String nSS) throws ExcepcionServicio;
}
