/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.application.service;

import java.util.List;

import com.domain.dto.HospitalDTO;

/**
 *
 * @author ck
 */
public interface ServiciosHospitalI {

	List<HospitalDTO> buscarTodosH();

	long save(HospitalDTO dto);

	void eliminarHospital(long id);

	HospitalDTO buscarHospital(long id);

}
