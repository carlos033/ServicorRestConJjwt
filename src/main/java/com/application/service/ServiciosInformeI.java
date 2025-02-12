/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.service;

import java.util.List;

import com.domain.exception.ExcepcionServicio;
import com.domain.model.Informe;

/**
 *
 * @author ck
 */
public interface ServiciosInformeI {

    public Informe crearInforme(Informe informe) throws ExcepcionServicio;

    public List<Informe> buscarTodosI();

    public void saveInformes(Informe informe1) throws ExcepcionServicio;

    public void eliminarInforme(String nombre) throws ExcepcionServicio;

    public List<Informe> buscarInformesXPaciente(String nSS) throws ExcepcionServicio;

    public void eliminarTodosXPaciente(String nSS) throws ExcepcionServicio;

}
