/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.serviciosI;

import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Medico;
import com.proyecto.modelos.Paciente;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author ck
 */
public interface ServiciosMedicoI {

	public List<Medico> buscarTodosM();

	public void saveMedico(Medico medico1);

	public void eliminarMedico(String nLicencia) throws ExcepcionServicio;

	public Optional<Medico> buscarMedico(String nLicencia) throws ExcepcionServicio;

	public List<Paciente> BuscarPacientesXMedico(String nLicencia) throws ExcepcionServicio;

	public List<Medico> BuscarMedicoXEspecialidad(String especialidad, String hospital) throws ExcepcionServicio;

	public List<Medico> BuscarMedicosXHospital(String hospital) throws ExcepcionServicio;

}
