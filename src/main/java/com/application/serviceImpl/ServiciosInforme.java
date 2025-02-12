/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.application.serviceImpl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.service.ServiciosInformeI;
import com.domain.exception.ExcepcionServicio;
import com.domain.model.Informe;
import com.domain.model.Medico;
import com.domain.model.Paciente;
import com.infrastructure.repository.InformeRepository;
import com.infrastructure.repository.MedicoRepository;
import com.infrastructure.repository.PacienteRepository;

import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@Service
@Transactional
public class ServiciosInforme implements ServiciosInformeI {

	private InformeRepository repositorioI;

	private PacienteRepository repositorioP;

	private MedicoRepository repositorioM;

	@Override
	@Transactional(readOnly = true)
	public List<Informe> buscarTodosI() {
		return repositorioI.findAll();
	}

	@Override
	public void saveInformes(Informe informe) throws ExcepcionServicio {
		repositorioI.save(informe);
	}

	@Override
	public void eliminarInforme(String nombre) throws ExcepcionServicio {
		repositorioI.findById(nombre).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El nombre del informe no existe"));
		repositorioI.deleteById(nombre);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Informe> buscarInformesXPaciente(String nSS) throws ExcepcionServicio {
		repositorioP.findById(nSS).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El numero de SS no existe"));

		return repositorioI.buscarInformeXPaciente(nSS);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Informe> buscarInformesXMedico(String nLicencia) throws ExcepcionServicio {
		repositorioM.findById(nLicencia).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El numero de Licencia no existe"));

		return repositorioI.buscarInformeXMedico(nLicencia);
	}

	@Override
	@Transactional(readOnly = true)
	public void eliminarTodosXPaciente(String nSS) throws ExcepcionServicio {
		List<Informe> listaCitas = buscarInformesXPaciente(nSS);
		repositorioI.deleteAllInBatch(listaCitas);
	}

	@Override
	public Informe crearInforme(Informe informe) throws ExcepcionServicio {
		Medico medico = repositorioM.findById(informe.getMedico().getNumLicencia()).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El numero de Licencia no existe"));
		Paciente paciente = repositorioP.findById(informe.getPaciente().getNss()).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El numero de SS no existe"));

		informe.setPaciente(paciente);
		informe.setMedico(medico);
		return this.repositorioI.save(informe);
	}
}
