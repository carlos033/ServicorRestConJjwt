package com.infrastructure.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.domain.dto.CitaDTO;
import com.domain.dto.InformeDTO;
import com.domain.dto.PacienteDTO;
import com.domain.model.Cita;
import com.domain.model.Informe;
import com.domain.model.Paciente;

@Mapper(componentModel = "spring")
public interface MappersPacientes {

	@BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "fechaNacimiento", source = "fechaNacimiento")
	@Mapping(target = "listaInformes", source = "listaInformes")
	@Mapping(target = "listaCitas", source = "listaCitas")
	@Mapping(target = "nombre", source = "nombre")
	@Mapping(target = "nss", source = "nss")
	@Mapping(target = "password", source = "password")
	PacienteDTO toDTOPaciente(Paciente paciente);

	@BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "nombreInf", source = "nombreInf")
	@Mapping(target = "url", source = "url")
	@Mapping(target = "medico.numLicencia", source = "medico.numLicencia")
	InformeDTO toDTOInforme(Informe informe);

	@BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "fechaCita", source = "fechaCita")
	@Mapping(target = "id", source = "id")
	@Mapping(target = "medico.numLicencia", source = "medico.numLicencia")
	CitaDTO toDTOCita(Cita cita);

	@InheritInverseConfiguration
	Paciente toEntityPaciente(PacienteDTO dto);

	@InheritInverseConfiguration
	Informe toEntityInforme(InformeDTO dto);

	@InheritInverseConfiguration
	Cita toEntityCita(CitaDTO dto);

}
