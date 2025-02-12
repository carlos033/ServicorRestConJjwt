/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.service;

import java.util.List;

import com.domain.exception.ExcepcionServicio;
import com.domain.model.Cita;
import com.domain.model.Medico;

/**
 *
 * @author ck
 */
public interface ServiciosCitaI {

    public Cita crearCita(Cita cita) throws ExcepcionServicio;

    public void save(Cita cita);

    public List<Cita> buscarTodasC();

    public List<Cita> buscarXPaciente(String nSS) throws ExcepcionServicio;

    public List<Cita> buscarXMedico(String nLicencia) throws ExcepcionServicio;

    public void eliminarCita(int id) throws ExcepcionServicio;

    public Medico buscarMiMedico(String nSS) throws ExcepcionServicio;

    public void eliminarTodasXPaciente(String nSS) throws ExcepcionServicio;

    public Cita buscarXId(int id) throws ExcepcionServicio;

}
