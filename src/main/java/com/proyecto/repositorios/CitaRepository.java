/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.repositorios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.modelos.Cita;
import com.proyecto.modelos.Medico;

/**
 *
 * @author ck
 */
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    String sqlBuscarMedicoCabecera = "Select m from Medico as m join m.listaCitas as medicocitas "
            + "join medicocitas.paciente as p where p.nSS= :nSS and "
            + "m.especialidad like 'Atencion primaria'";

    @Query(value = "Select c from Cita c where c.paciente.nSS= :nss")
    public List<Cita> buscarCitaXPaciente(@Param("nss") String nSS);

    @Query(value = "Select c from Cita c where c.medico.nLicencia = :nLicencia")
    public List<Cita> buscarCitaXMedico(@Param("nLicencia") String nLicencia);

    @Query(value = "Select c from Cita c where c.paciente.nSS= :nss and c.fHoraCita = :fecha")
    public List<Cita> buscarCitaXPacienteYHora(@Param("nss") String nSS, @Param("fecha") Date fecha);

    @Query(value = "Select c from Cita c where c.medico.nLicencia = :nLicencia and c.fHoraCita = :fecha")
    public List<Cita> buscarCitaXMedicoYHora(@Param("nLicencia") String nLicencia, @Param("fecha") Date fecha);

    @Query(value = sqlBuscarMedicoCabecera)
    public Optional<Medico> buscarMmedico(@Param("nSS") String nSS);

}
