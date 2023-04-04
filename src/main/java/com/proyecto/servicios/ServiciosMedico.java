/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.servicios;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Hospital;
import com.proyecto.modelos.Medico;
import com.proyecto.modelos.Paciente;
import com.proyecto.repositorios.HospitalRepository;
import com.proyecto.repositorios.MedicoRepository;
import com.proyecto.serviciosI.ServiciosMedicoI;

/**
 *
 * @author ck
 */
@Service("ServiciosMedicoI")
public class ServiciosMedico implements ServiciosMedicoI {

	@Autowired
	private MedicoRepository repositorioM;
	@Autowired
	private HospitalRepository repositorioH;
	@Autowired	
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public List<Medico> buscarTodosM() {
		return repositorioM.findAll();
	}

	@Override
	@Transactional
	public void saveMedico(Medico medico1) {
		medico1.setPassword(passwordEncoder.encode(medico1.getPassword()));
		repositorioM.save(medico1);
	}

	@Override
	public void eliminarMedico(String nLicencia) throws ExcepcionServicio {
		Optional<Medico> optMedico = repositorioM.findById(nLicencia);
		if (!optMedico.isPresent()) {
			throw new ExcepcionServicio("El numero de Licencia no existe");
		}
		repositorioM.deleteById(nLicencia);
	}

	@Override
	public Optional<Medico> buscarMedico(String nLicencia) throws ExcepcionServicio {
		Optional<Medico> optMedico = repositorioM.findById(nLicencia);
		if (!optMedico.isPresent()) {
			throw new ExcepcionServicio("El numero de Licencia no existe");
		}
		return optMedico;
	}

	@Override
	public List<Paciente> BuscarPacientesXMedico(String nLicencia) throws ExcepcionServicio {
		List<Paciente> listaPacientes = repositorioM.BuscarPacientesXMedico(nLicencia);
		if (listaPacientes.isEmpty()) {
			throw new ExcepcionServicio("El numero de Licencia no existe");
		}
		return listaPacientes;
	}

	@Override
	public List<Medico> BuscarMedicoXEspecialidad(String especialidad, String hospital) throws ExcepcionServicio {
		Optional<Hospital> optHospital = repositorioH.findById(hospital);
		if (!optHospital.isPresent()) {
			throw new ExcepcionServicio("El hospital no existe");
		}
		List<Medico> listaMedico = repositorioM.BuscarMedicoXEspecialidad(especialidad, hospital);
		if (listaMedico.isEmpty()) {
			throw new ExcepcionServicio("No hay medicos con esa especialidad");
		}
		return listaMedico;
	}

	@Override
	public List<Medico> BuscarMedicosXHospital(String hospital) throws ExcepcionServicio {
		List<Medico> listaMedico = repositorioM.BuscarMedicosXHospital(hospital);
		if (listaMedico.isEmpty()) {
			throw new ExcepcionServicio("No hay medicos en el hospital");
		}
		return listaMedico;
	}
}
