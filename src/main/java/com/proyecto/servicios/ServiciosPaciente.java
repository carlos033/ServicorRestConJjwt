/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Paciente;
import com.proyecto.repositorios.PacienteRepository;
import com.proyecto.serviciosI.ServiciosPacienteI;

import jakarta.transaction.Transactional;

/**
 *
 * @author ck
 */
@Service("ServiciosPacienteI")
@Transactional
public class ServiciosPaciente implements ServiciosPacienteI {

    @Autowired
    private PacienteRepository repositorioP;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Paciente> buscarTodosP() {
        return repositorioP.findAll();
    }

    @Override
    public void eliminarPaciente(String nSS) throws ExcepcionServicio {
        buscarPaciente(nSS);
        repositorioP.deleteById(nSS);
    }

    @Override
    public void savePaciente(Paciente paciente1) {
        paciente1.setPassword(passwordEncoder.encode(paciente1.getPassword()));
        repositorioP.save(paciente1);
    }

    @Override
    public Optional<Paciente> buscarPaciente(String nSS) throws ExcepcionServicio {
        Optional<Paciente> optPaciente = repositorioP.findById(nSS);
        if (!optPaciente.isPresent()) {
            throw new ExcepcionServicio("El numero de SS no existe");
        }
        return optPaciente;
    }
}
