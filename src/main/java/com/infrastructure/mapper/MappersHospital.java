package com.infrastructure.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.domain.dto.HospitalDTO;
import com.domain.dto.MedicoDTO;
import com.domain.model.Hospital;
import com.domain.model.Medico;

@Mapper(componentModel = "spring")
public interface MappersHospital {

  @BeanMapping(ignoreByDefault = true,
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "nombreHos", source = "nombreHos")
  @Mapping(target = "poblacion", source = "poblacion")
  @Mapping(target = "numConsultas", source = "numConsultas")
  @Mapping(target = "listaMedicos", source = "listaMedicos")
  HospitalDTO toDTOHospital(Hospital entity);

  @BeanMapping(ignoreByDefault = true,
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "numLicencia", source = "numLicencia")
  MedicoDTO toDTOMedico(Medico paciente);

  @InheritInverseConfiguration
  Hospital toEntityHospital(HospitalDTO dto);

  @InheritInverseConfiguration
  Medico toEntityPaciente(MedicoDTO dto);

}
