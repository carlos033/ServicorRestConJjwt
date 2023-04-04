/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto.dto.CitaDTO;
import com.proyecto.dto.InformeMedicoDTO;
import com.proyecto.dto.MedicoDTO;
import com.proyecto.dto.PacienteDTO;
import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Cita;
import com.proyecto.modelos.Informe;
import com.proyecto.modelos.Medico;
import com.proyecto.modelos.Paciente;
import com.proyecto.serviciosI.ServiciosCitaI;
import com.proyecto.serviciosI.ServiciosInformeI;
import com.proyecto.serviciosI.ServiciosMedicoI;
import com.proyecto.utiles.Transformadores;

/**
 *
 * @author ck
 */
@RestController
@RequestMapping(path = "/medicos")
public class MedicoJpaController {

    @Autowired
    private Transformadores transformador;
    @Autowired
    private ServiciosMedicoI sMedico;
    @Autowired
    private ServiciosCitaI sCita;
    @Autowired
    private ServiciosInformeI sInformes;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public MedicoDTO aniadirMedico(@Valid @RequestBody MedicoDTO medicoDTO) {
        Medico medico = transformador.convertirAEntidadM(medicoDTO);
        MedicoDTO resultado;
		sMedico.saveMedico(medico);
        resultado = transformador.convertirADTOM(medico);
        return resultado;
    }

    @DeleteMapping("/{nLicencia}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarMedico(@PathVariable("nLicencia") String nLicencia) {
        try {
            sMedico.eliminarMedico(nLicencia);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
    }

    @GetMapping("/{nLicencia}")
    @ResponseBody
    public MedicoDTO buscarMedico(@PathVariable("nLicencia") String nLicencia) {
        Medico m = new Medico();
        try {
            Optional<Medico> optM = sMedico.buscarMedico(nLicencia);
            m = optM.get();
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
        return transformador.convertirADTOM(m);
    }

    @GetMapping
    @ResponseBody
    public List<MedicoDTO> listMedicos() {
        List<Medico> listaMedicos = sMedico.buscarTodosM();
        return listaMedicos.stream().map(transformador::convertirADTOM).collect(Collectors.toList());
    }

    @GetMapping("/{nLicencia}/citas")
    @ResponseBody
    public List<CitaDTO> buscarCitaXMedico(@PathVariable("nLicencia") String nLicencia) {
        List<Cita> listaCitas = new ArrayList<>();
        try {
            listaCitas = sCita.buscarXMedico(nLicencia);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
        return listaCitas.stream().map(transformador::convertirADTOC).collect(Collectors.toList());
    }

    @GetMapping("/{nLicencia}/pacientes")
    @ResponseBody
    public List<PacienteDTO> buscarPacienteXMedico(@PathVariable("nLicencia") String nLicencia) {
        List<Paciente> listaPacientes = new ArrayList<>();
        try {
            listaPacientes = sMedico.BuscarPacientesXMedico(nLicencia);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
        return listaPacientes.stream().map(transformador::convertirADTOP).collect(Collectors.toList());
    }

    @GetMapping("/{especialidad}/{nombrehos}/hospital")
    @ResponseBody
    public List<MedicoDTO> BuscarMedicoXEspecialidad(@PathVariable("especialidad") String especialidad,
            @PathVariable("nombrehos") String nombrehos) {
        List<Medico> listaMedicos = new ArrayList<>();
        try {
            listaMedicos = sMedico.BuscarMedicoXEspecialidad(especialidad, nombrehos);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
        return listaMedicos.stream().map(transformador::convertirADTOM).collect(Collectors.toList());
    }

    @GetMapping("/{nombrehos}/hospital")
    @ResponseBody
    public List<MedicoDTO> BuscarMedicosXHospital(@PathVariable("nombrehos") String nombrehos) {
        List<Medico> listaMedicos = new ArrayList<>();
        try {
            listaMedicos = sMedico.BuscarMedicosXHospital(nombrehos);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
        return listaMedicos.stream().map(transformador::convertirADTOM).collect(Collectors.toList());
    }

    @GetMapping("/{nLicencia}/informes")
    @ResponseBody
    public List<InformeMedicoDTO> buscarInformesXPaciente(@PathVariable("nLicencia") String nLicencia) {
        List<Informe> listaInformes = new ArrayList<>();
        try {
            listaInformes = sInformes.buscarInformesXMedico(nLicencia);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
        return listaInformes.stream().map(transformador::convertirADTOIM).collect(Collectors.toList());
    }
}
