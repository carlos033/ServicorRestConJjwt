/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.application.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.service.ServicioHospita;
import com.domain.dto.HospitalDTO;
import com.infrastructure.adaptador.impl.AdaptadorHospitalImpl;

import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@Service
@Transactional
public class ServiciosHospital implements ServicioHospita {

	private AdaptadorHospitalImpl adaptador;

	@Override
	public List<HospitalDTO> buscarTodosH() {
		return adaptador.buscarTodosH();
	}

	@Override
	public long save(HospitalDTO dto) {
		return adaptador.save(dto);
	}

	@Override
	public void eliminarHospital(long id) {
		adaptador.eliminarHospital(id);
	}

	@Override
	public HospitalDTO buscarHospital(long id) {
		return adaptador.buscarHospital(id);
	}
}
