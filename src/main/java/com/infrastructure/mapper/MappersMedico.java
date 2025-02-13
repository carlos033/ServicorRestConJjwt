package com.infrastructure.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.domain.dto.CitaDTO;
import com.domain.dto.InformeDTO;
import com.domain.dto.MedicoDTO;
import com.domain.model.Cita;
import com.domain.model.Informe;
import com.domain.model.Medico;

@Mapper(componentModel = "spring")
public interface MappersMedico {

	@BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "consulta", source = "consulta")
	@Mapping(target = "listaInformes", source = "listaInformes")
	@Mapping(target = "listaCitas", source = "listaCitas")
	@Mapping(target = "especialidad", source = "especialidad")
	@Mapping(target = "hospital.nombreHos", source = "hospital.nombreHos")
	@Mapping(target = "nombre", source = "nombre")
	@Mapping(target = "numLicencia", source = "numLicencia")
	@Mapping(target = "password", source = "password")
	MedicoDTO toDTOMedico(Medico paciente);

	@BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "nombreInf", source = "nombreInf")
	@Mapping(target = "url", source = "url")
	@Mapping(target = "paciente.nss", source = "paciente.nss")
	InformeDTO toDTOInforme(Informe informe);

	@BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "fechaCita", source = "fechaCita")
	@Mapping(target = "id", source = "id")
	@Mapping(target = "paciente.nss", source = "paciente.nss")
	CitaDTO toDTOCita(Cita cita);

	@InheritInverseConfiguration
	Medico toEntityMedico(MedicoDTO dto);

	@InheritInverseConfiguration
	Informe toEntityInforme(InformeDTO dto);

	@InheritInverseConfiguration
	Cita toEntityCita(CitaDTO dto);
}
