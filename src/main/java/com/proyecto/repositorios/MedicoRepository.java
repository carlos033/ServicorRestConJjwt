/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
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

	String SQL_BUSCAR_PACIENTES_POR_MEDICO = """
	        SELECT p
	        FROM Paciente
	        p JOIN
	        p.listaCitas pacientecitas
	        JOIN pacientecitas.
	        medico m
	        WHERE m.nLicencia=:nLicencia""";

	String SQL_BUSCAR_MEDICO_POR_ESPECIALIDAD_HOSPITAL = """
	        Select m
	        from Medico as m
	        where m.especialidad = :especialidad and m.hospital.nombreHos = :nombreHospital""";

	String SQL_BUSCAR_MEDICOS_POR_HOSPITAL = """
	        Select m
	        from Medico as m
	        where m.hospital.nombreHos = :nombreHospital""";

	@Query(SQL_BUSCAR_PACIENTES_POR_MEDICO)
	public List<Paciente> BuscarPacientesXMedico(@Param("nLicencia") String nLicencia);

	@Query(SQL_BUSCAR_MEDICO_POR_ESPECIALIDAD_HOSPITAL)
	public List<Medico> BuscarMedicoXEspecialidad(@Param("especialidad") String especialidad, @Param("nombreHospital") String hospital);

	@Query(SQL_BUSCAR_MEDICOS_POR_HOSPITAL)
	public List<Medico> BuscarMedicosXHospital(@Param("nombreHospital") String hospitalNombre);

}
