/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.proyecto.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Cita;
import com.proyecto.modelos.Medico;
import com.proyecto.modelos.Paciente;
import com.proyecto.repositorios.CitaRepository;
import com.proyecto.repositorios.MedicoRepository;
import com.proyecto.repositorios.PacienteRepository;
import com.proyecto.serviciosI.ServiciosCitaI;

import jakarta.transaction.Transactional;
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
		Date fechaDHoy = new Date();
		Date fecha = cita.getFHoraCita();
		String nss = cita.getPaciente().getNSS();
		String nLicencia = cita.getMedico().getnLicencia();
		List<Cita> citasPacienteEsaHora = repositorioC.buscarCitaXPacienteYHora(nss, fecha);
		List<Cita> citasMedicoEsaHora = repositorioC.buscarCitaXMedicoYHora(nLicencia, fecha);
		if (citasPacienteEsaHora.isEmpty()) {
			throw new ExcepcionServicio("Usted ya tiene una cita en esa fecha y hora");
		}
		if (citasMedicoEsaHora.isEmpty()) {
			throw new ExcepcionServicio("El medico no tiene hueco a esa hora ese dia");
		}
		if (fecha.before(fechaDHoy)) {
			throw new ExcepcionServicio("La fecha debe ser posterior a hoy");
		}
		Medico medico = repositorioM.findById(nLicencia).orElseThrow(() -> new ExcepcionServicio("El número de licencia no existe"));

		Paciente paciente = repositorioP.findById(nss).orElseThrow(() -> new ExcepcionServicio("El número de SS no existe"));

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
	public List<Cita> buscarXPaciente(String nSS) throws ExcepcionServicio {
		List<Cita> listaCitas = repositorioC.buscarCitaXPaciente(nSS);
		if (listaCitas.isEmpty()) {
			throw new ExcepcionServicio("el nSS no existe");
		}
		return listaCitas;
	}

	@Override
	public List<Cita> buscarXMedico(String nLicencia) throws ExcepcionServicio {
		List<Cita> listaCitas = repositorioC.buscarCitaXMedico(nLicencia);
		if (listaCitas.isEmpty()) {
			throw new ExcepcionServicio("el numero de licencia no existe");
		}
		return listaCitas;
	}

	@Override
	public void eliminarTodasXPaciente(String nSS) throws ExcepcionServicio {
		List<Cita> listaCitas = buscarXPaciente(nSS);
		if (listaCitas.isEmpty()) {
			throw new ExcepcionServicio("El numero de SS no existe");
		}
		repositorioC.deleteAllInBatch(listaCitas);
	}

	@Override
	public Medico buscarMiMedico(String nSS) throws ExcepcionServicio {
		return repositorioC.buscarMmedico(nSS).orElseThrow(() -> new ExcepcionServicio("Paciente con NSS no existe o no tiene citas"));
	}

	@Override
	public Cita buscarXId(int id) throws ExcepcionServicio {
		return repositorioC.findById(id).orElseThrow(() -> new ExcepcionServicio("La ID no existe"));
	}
}
