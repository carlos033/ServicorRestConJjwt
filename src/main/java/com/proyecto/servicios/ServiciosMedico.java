/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.proyecto.servicios;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Medico;
import com.proyecto.modelos.Paciente;
import com.proyecto.repositorios.HospitalRepository;
import com.proyecto.repositorios.MedicoRepository;
import com.proyecto.serviciosI.ServiciosMedicoI;

import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@Service
public class ServiciosMedico implements ServiciosMedicoI {

	private MedicoRepository repositorioM;

	private HospitalRepository repositorioH;

	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
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
		repositorioM.findById(nLicencia).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El numero de Licencia no existe"));

		repositorioM.deleteById(nLicencia);
	}

	@Override
	@Transactional(readOnly = true)
	public Medico buscarMedico(String nLicencia) throws ExcepcionServicio {

		return repositorioM.findById(nLicencia).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El numero de Licencia no existe"));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Paciente> BuscarPacientesXMedico(String nLicencia) throws ExcepcionServicio {
		List<Paciente> listaPacientes = repositorioM.BuscarPacientesXMedico(nLicencia);
		if (listaPacientes.isEmpty()) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "El numero de Licencia no existe");
		}
		return listaPacientes;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Medico> BuscarMedicoXEspecialidad(String especialidad, String hospital) throws ExcepcionServicio {
		repositorioH.findById(hospital).orElseThrow(() -> new ExcepcionServicio(HttpStatus.NOT_FOUND, "El hospital no existe"));
		List<Medico> listaMedico = repositorioM.BuscarMedicoXEspecialidad(especialidad, hospital);
		if (listaMedico.isEmpty()) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "No hay medicos con esa especialidad");
		}
		return listaMedico;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Medico> BuscarMedicosXHospital(String hospital) throws ExcepcionServicio {
		List<Medico> listaMedico = repositorioM.BuscarMedicosXHospital(hospital);
		if (listaMedico.isEmpty()) {
			throw new ExcepcionServicio(HttpStatus.NOT_FOUND, "No hay medicos en el hospital");
		}
		return listaMedico;
	}
}
