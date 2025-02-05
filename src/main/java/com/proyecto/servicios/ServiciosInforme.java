/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.proyecto.servicios;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Informe;
import com.proyecto.modelos.Medico;
import com.proyecto.modelos.Paciente;
import com.proyecto.repositorios.InformeRepository;
import com.proyecto.repositorios.MedicoRepository;
import com.proyecto.repositorios.PacienteRepository;
import com.proyecto.serviciosI.ServiciosInformeI;

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
		Medico medico = repositorioM.findById(informe.getMedico().getnLicencia()).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El numero de Licencia no existe"));
		Paciente paciente = repositorioP.findById(informe.getPaciente().getNSS()).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El numero de SS no existe"));

		informe.setPaciente(paciente);
		informe.setMedico(medico);
		return this.repositorioI.save(informe);
	}
}
