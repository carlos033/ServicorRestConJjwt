/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.serviciosI;

import java.util.List;

import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Hospital;

/**
 *
 * @author ck
 */
public interface ServiciosHospitalI {

    public List<Hospital> buscarTodosH();

    public void save(Hospital hospital1);

    public void eliminarHospital(String nombre) throws ExcepcionServicio;

	public Hospital buscarHospital(String nombre) throws ExcepcionServicio;
}
