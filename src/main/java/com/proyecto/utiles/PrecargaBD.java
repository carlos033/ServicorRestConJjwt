/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.proyecto.utiles;

import org.springframework.stereotype.Component;

import com.domain.dto.MedicoDTO;
import com.infrastructure.adaptador.impl.AdaptadorMedicoImpl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@Component
public class PrecargaBD {

	private AdaptadorMedicoImpl adaptador;

	@Transactional
	public void precargarBaseDeDatos() {
		MedicoDTO dto = adaptador.buscarMedico("M1");
		if (dto == null) {
			MedicoDTO m = new MedicoDTO("M1", "Admin", "Administrador", 0, "1234", null, null, null);
			adaptador.saveMedico(m);
		}
	}

}
