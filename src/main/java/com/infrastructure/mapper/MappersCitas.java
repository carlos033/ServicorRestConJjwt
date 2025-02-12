package com.infrastructure.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.domain.dto.CitaDTO;
import com.domain.model.Cita;

@Mapper(componentModel = "spring")
public interface MappersCitas {

	@BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "fechaCita", source = "fechaCita")
	@Mapping(target = "id", source = "id")
	@Mapping(target = "medico.numLicencia", source = "medico.numLicencia")
	@Mapping(target = "paciente.nss", source = "paciente.nss")
	CitaDTO toDTOCita(Cita cita);

	@InheritInverseConfiguration
	Cita toEntityCita(CitaDTO dto);
}
