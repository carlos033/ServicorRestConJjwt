/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.servicios;

import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.serviciosI.ServiciosCitaI;
import com.proyecto.modelos.Cita;
import com.proyecto.modelos.Medico;
import com.proyecto.modelos.Paciente;
import com.proyecto.repositorios.CitaRepository;
import com.proyecto.repositorios.MedicoRepository;
import com.proyecto.repositorios.PacienteRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ck
 */
@Service("ServiciosCitaI")
@Transactional
public class ServiciosCita implements ServiciosCitaI {

	@Autowired
	private CitaRepository repositorioC;

	@Autowired
	private MedicoRepository repositorioM;

	@Autowired
	private PacienteRepository repositorioP;

	@Override
	public void save(Cita cita) {
		repositorioC.save(cita);
	}

	@Override
	public Cita crearCita(Cita cita) throws ExcepcionServicio {
		Date fechaDHoy = new Date();
		Date fecha = cita.getfHoraCita();
		String nss = cita.getPaciente().getnSS();
		String nLicencia = cita.getMedico().getnLicencia();
		List<Cita> citasPacienteEsaHora = repositorioC.buscarCitaXPacienteYHora(nss, fecha);
		List<Cita> citasMedicoEsaHora = repositorioC.buscarCitaXMedicoYHora(nLicencia, fecha);
		if (citasPacienteEsaHora.size() > 0) {
			throw new ExcepcionServicio("Usted ya tiene una cita en esa fecha y hora");
		}
		if (citasMedicoEsaHora.size() > 0) {
			throw new ExcepcionServicio("El medico no tiene hueco a esa hora ese dia");
		}
		if (fecha.before(fechaDHoy)) {
			throw new ExcepcionServicio("La fecha debe ser posterior a hoy");
		}
		Optional<Medico> m = repositorioM.findById(nLicencia);
		Optional<Paciente> p = repositorioP.findById(nss);
		if (!m.isPresent()) {
			throw new ExcepcionServicio("El numero de licencia no existe");
		}
		if (!p.isPresent()) {
			throw new ExcepcionServicio("El numero de SS no existe");
		}
		cita.setPaciente(p.get());
		cita.setMedico(m.get());
		return this.repositorioC.save(cita);
	}

	@Override
	public List<Cita> buscarTodasC() {
		List<Cita> listaCitas = repositorioC.findAll();
		return listaCitas;
	}

	@Override
	public void eliminarCita(int id) throws ExcepcionServicio {
		try {
			buscarXId(id);
		} catch (ExcepcionServicio ex) {
			Logger.getLogger(ServiciosCita.class.getName()).log(Level.SEVERE, null, ex);
		}
		repositorioC.deleteById(id);
	}

	@Override
	public List<Cita> buscarXPaciente(String nSS) throws ExcepcionServicio {
		List<Cita> listaCitas = repositorioC.buscarCitaXPaciente(nSS);
		if (listaCitas == null) {
			throw new ExcepcionServicio("el nSS no existe");
		}
		return listaCitas;
	}

	@Override
	public List<Cita> buscarXMedico(String nLicencia) throws ExcepcionServicio {
		List<Cita> listaCitas = repositorioC.buscarCitaXMedico(nLicencia);
		if (listaCitas == null) {
			throw new ExcepcionServicio("el numero de licencia no existe");
		}
		return listaCitas;
	}

	@Override
	public void eliminarTodasXPaciente(String nSS) throws ExcepcionServicio {
		List<Cita> listaCitas = buscarXPaciente(nSS);
		if (listaCitas == null) {
			throw new ExcepcionServicio("El numero de SS no existe");
		}
		repositorioC.deleteAllInBatch(listaCitas);
	}

	@Override
	public Medico buscarMiMedico(String nSS) throws ExcepcionServicio {
		Optional<Medico> optMedico = repositorioC.buscarMmedico(nSS);
		if (!optMedico.isPresent()) {
			throw new ExcepcionServicio("Paciente con nSS no existe o no tiene citas");
		}
		Medico medico = optMedico.get();
		return medico;
	}

	@Override
	public Cita buscarXId(int id) throws ExcepcionServicio {
		Optional<Cita> optCita = repositorioC.findById(id);
		if (!optCita.isPresent()) {
			throw new ExcepcionServicio("La id no existe");
		}
		Cita cita = optCita.get();
		return cita;
	}
}
