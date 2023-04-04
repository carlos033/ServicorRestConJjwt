/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.repositorios;

import com.proyecto.modelos.Medico;
import com.proyecto.modelos.Paciente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ck
 */

public interface MedicoRepository extends JpaRepository<Medico, String> {

    String BuscarPacientesXMedico = "Select p.listaCitas from Paciente as p join p.listaCitas as pacientecitas "
            + "join pacientecitas.medico as m where m.nLicencia= :nLicencia ";

    String BuscarMedicoXEspecialidad = "Select m from Medico as m where m.especialidad = :especialidad and m.hospital.nombreHos = :nombreHospital";

    String Hospital = "Select m from Medico as m where m.hospital.nombreHos = :nombreHospital";

    @Query(value = BuscarPacientesXMedico)
    public List<Paciente> BuscarPacientesXMedico(@Param("nLicencia") String nLicencia);

    @Query(value = BuscarMedicoXEspecialidad)
    public List<Medico> BuscarMedicoXEspecialidad(@Param("especialidad") String especialidad, @Param("nombreHospital") String hospital);
    
    @Query(value = Hospital)
    public List<Medico> BuscarMedicosXHospital(@Param("nombreHospital") String hospitalNombre);
    
}
