/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.utiles;

import org.springframework.stereotype.Component;

import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Medico;
import com.proyecto.servicios.ServiciosMedico;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@Component
public class PrecargaBD {
	
	private ServiciosMedico sMedico;

	@Transactional
	public void precargarBaseDeDatos() {
		try {
			// lanza excepcion si no lo encuentra, entonces en ese caso precargamos
			sMedico.buscarMedico("M1");
		} catch (ExcepcionServicio ex) {
			Medico m = new Medico("M1", "Admin", "Administrador", 0, "1234", null, null, null);
			sMedico.saveMedico(m);
		}
	}

}
