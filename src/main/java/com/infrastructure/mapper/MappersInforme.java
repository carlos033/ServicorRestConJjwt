package com.infrastructure.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.domain.dto.InformeDTO;
import com.domain.model.Informe;
import com.domain.model.Medico;
import com.domain.model.Paciente;

@Mapper(componentModel = "spring")
public interface MappersInforme {

	@BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "nombreInf", source = "nombreInf")
	@Mapping(target = "url", source = "url")
	@Mapping(target = "medico.numLicencia", source = "medico.numLicencia")
	@Mapping(target = "paciente.nss", source = "paciente.nss")
	InformeDTO toDTOInforme(Informe informe);

	@BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "nombreInf", source = "dto.nombreInf")
	@Mapping(target = "url", source = "dto.url")
	@Mapping(target = "paciente", source = "paciente")
	@Mapping(target = "medico", source = "medico")
	Informe toEntityInforme(InformeDTO dto, @Context Paciente paciente, @Context Medico medico);

}
