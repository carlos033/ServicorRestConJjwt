/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.application.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.service.ServiciosCitaI;
import com.domain.exception.ExcepcionServicio;
import com.domain.model.Cita;
import com.domain.model.Medico;
import com.domain.model.Paciente;
import com.infrastructure.repository.CitaRepository;
import com.infrastructure.repository.MedicoRepository;
import com.infrastructure.repository.PacienteRepository;

import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@Service("ServiciosCitaI")
@Transactional
public class ServiciosCita implements ServiciosCitaI {

	private final CitaRepository repositorioC;

	private final MedicoRepository repositorioM;

	private final PacienteRepository repositorioP;

	@Override
	public void save(Cita cita) {
		repositorioC.save(cita);
	}

	@Override
	public Cita crearCita(Cita cita) throws ExcepcionServicio {
		LocalDateTime fechaDHoy = LocalDateTime.now();
		LocalDateTime fecha = cita.getFechaCita();
		String nss = cita.getPaciente().getNss();
		String nLicencia = cita.getMedico().getNumLicencia();
		List<Cita> citasPacienteEsaHora = repositorioC.buscarCitaXPacienteYHora(nss, fecha);
		List<Cita> citasMedicoEsaHora = repositorioC.buscarCitaXMedicoYHora(nLicencia, fecha);
		if (citasPacienteEsaHora.isEmpty()) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "Usted ya tiene una cita en esa fecha y hora");
		}
		if (citasMedicoEsaHora.isEmpty()) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "El medico no tiene hueco a esa hora ese dia");
		}
		if (fecha.isBefore(fechaDHoy)) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "La fecha debe ser posterior a hoy");
		}
		Medico medico = repositorioM.findById(nLicencia).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El número de licencia no existe"));

		Paciente paciente = repositorioP.findById(nss).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El número de SS no existe"));

		cita.setPaciente(paciente);
		cita.setMedico(medico);
		return this.repositorioC.save(cita);
	}

	@Override
	public List<Cita> buscarTodasC() {
		return repositorioC.findAll();
	}

	@Override
	public void eliminarCita(int id) throws ExcepcionServicio {
		buscarXId(id);
		repositorioC.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cita> buscarXPaciente(String nSS) throws ExcepcionServicio {
		List<Cita> listaCitas = repositorioC.buscarCitaXPaciente(nSS);
		if (listaCitas.isEmpty()) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "el nSS no existe");
		}
		return listaCitas;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cita> buscarXMedico(String nLicencia) throws ExcepcionServicio {
		List<Cita> listaCitas = repositorioC.buscarCitaXMedico(nLicencia);
		if (listaCitas.isEmpty()) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "el numero de licencia no existe");
		}
		return listaCitas;
	}

	@Override
	@Transactional(readOnly = true)
	public void eliminarTodasXPaciente(String nSS) throws ExcepcionServicio {
		List<Cita> listaCitas = buscarXPaciente(nSS);
		if (listaCitas.isEmpty()) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "El numero de SS no existe");
		}
		repositorioC.deleteAllInBatch(listaCitas);
	}

	@Override
	@Transactional(readOnly = true)
	public Medico buscarMiMedico(String nSS) throws ExcepcionServicio {
		return repositorioC.buscarMmedico(nSS).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "Paciente con NSS no existe o no tiene citas"));
	}

	@Override
	@Transactional(readOnly = true)
	public Cita buscarXId(int id) throws ExcepcionServicio {
		return repositorioC.findById(id).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "La ID no existe"));
	}
}
