/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.utiles;

import com.proyecto.dto.CitaDTO;
import com.proyecto.dto.HospitalDTO;
import com.proyecto.dto.InformeDTO;
import com.proyecto.dto.InformeMedicoDTO;
import com.proyecto.dto.InformePacienteDTO;
import com.proyecto.dto.InformeCompletoDTO;
import com.proyecto.dto.MedicoDTO;
import com.proyecto.dto.PacienteDTO;
import com.proyecto.modelos.Cita;
import com.proyecto.modelos.Hospital;
import com.proyecto.modelos.Informe;
import com.proyecto.modelos.Medico;
import com.proyecto.modelos.Paciente;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ck
 */
@Component
public class Transformadores {

    @Autowired
    private ModelMapper modelMapper;

    public Cita convertirAEntidadC(CitaDTO dTO) {
        Cita entidad = modelMapper.map(dTO, Cita.class);
        return entidad;
    }

    public CitaDTO convertirADTOC(Cita entidad) {
        CitaDTO dTO = modelMapper.map(entidad, CitaDTO.class);
        return dTO;
    }

    public Hospital convertirAEntidadH(HospitalDTO dTO) {
        Hospital entidad = modelMapper.map(dTO, Hospital.class);
        return entidad;
    }

    public HospitalDTO convertirADTOH(Hospital entidad) {
        HospitalDTO dTO = modelMapper.map(entidad, HospitalDTO.class);
        return dTO;
    }

    public Informe convertirAEntidadI(InformeDTO dTO) {
        Informe entidad = modelMapper.map(dTO, Informe.class);
        return entidad;
    }

    public InformeDTO convertirADTOI(Informe entidad) {
        InformeDTO dTO = modelMapper.map(entidad, InformeDTO.class);
        return dTO;
    }

    public Medico convertirAEntidadM(MedicoDTO dTO) {
        Medico entidad = modelMapper.map(dTO, Medico.class);
        return entidad;
    }

    public MedicoDTO convertirADTOM(Medico entidad) {
        MedicoDTO dTO = modelMapper.map(entidad, MedicoDTO.class);
        return dTO;
    }

    public Paciente convertirAEntidadP(PacienteDTO dto) {
        Paciente entidad = modelMapper.map(dto, Paciente.class);
        return entidad;
    }

    public PacienteDTO convertirADTOP(Paciente entidad) {
        PacienteDTO dto = modelMapper.map(entidad, PacienteDTO.class);
        return dto;
    }

    public Informe convertirAEntidadI(InformePacienteDTO dTO) {
        Informe entidad = modelMapper.map(dTO, Informe.class);
        return entidad;
    }

    public InformePacienteDTO convertirADTOIP(Informe entidad) {
        InformePacienteDTO dTO = modelMapper.map(entidad, InformePacienteDTO.class);
        return dTO;
    }

    public Informe convertirAEntidadI(InformeMedicoDTO dTO) {
        Informe entidad = modelMapper.map(dTO, Informe.class);
        return entidad;
    }

    public InformeMedicoDTO convertirADTOIM(Informe entidad) {
        InformeMedicoDTO dTO = modelMapper.map(entidad, InformeMedicoDTO.class);
        return dTO;
    }

    public Informe convertirAEntidadI(InformeCompletoDTO dTO) {
        Informe entidad = modelMapper.map(dTO, Informe.class);
        return entidad;
    }
}
