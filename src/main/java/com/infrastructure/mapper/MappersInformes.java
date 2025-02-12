package com.infrastructure.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.domain.dto.InformeDTO;
import com.domain.model.Informe;

@Mapper(componentModel = "spring")
public interface MappersInformes {

	@BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "nombreInf", source = "nombreInf")
	@Mapping(target = "url", source = "url")
	@Mapping(target = "medico.numLicencia", source = "medico.numLicencia")
	@Mapping(target = "paciente.nss", source = "paciente.nss")
	InformeDTO toDTOInforme(Informe informe);

	@InheritInverseConfiguration
	Informe toEntityInforme(InformeDTO dto);
}
