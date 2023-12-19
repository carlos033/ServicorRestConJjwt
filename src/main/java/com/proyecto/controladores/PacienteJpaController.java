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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto.dto.CitaDTO;
import com.proyecto.dto.InformePacienteDTO;
import com.proyecto.dto.PacienteDTO;
import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Cita;
import com.proyecto.modelos.Informe;
import com.proyecto.modelos.Paciente;
import com.proyecto.serviciosI.ServiciosCitaI;
import com.proyecto.serviciosI.ServiciosInformeI;
import com.proyecto.serviciosI.ServiciosPacienteI;
import com.proyecto.utiles.Transformadores;

import jakarta.validation.Valid;

/**
 *
 * @author ck
 */
@RestController
@RequestMapping(path = "/pacientes")
public class PacienteJpaController {

    @Autowired
    private Transformadores transformador;
    @Autowired
    private ServiciosPacienteI sPaciente;
    @Autowired
    private ServiciosCitaI sCita;
    @Autowired
    private ServiciosInformeI sInformes;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public PacienteDTO aniadirPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
        Paciente paciente = transformador.convertirAEntidadP(pacienteDTO);
        PacienteDTO resultado;
        sPaciente.savePaciente(paciente);
        resultado = transformador.convertirADTOP(paciente);
        return resultado;
    }

    @DeleteMapping("/{nSS}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarPaciente(@PathVariable("nSS") String nSS) {
        try {
            sCita.eliminarTodasXPaciente(nSS);
            sPaciente.eliminarPaciente(nSS);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
    }

    @GetMapping
    @ResponseBody
    public List<PacienteDTO> listPacientes() {
        List<Paciente> listaPacientes = sPaciente.buscarTodosP();
        return listaPacientes.stream().map(transformador::convertirADTOP).collect(Collectors.toList());
    }

    @GetMapping("/{nSS}")
    @ResponseBody
    public PacienteDTO buscarPaciente(@RequestParam String nSS) {
        Optional<Paciente> optPaciente = Optional.empty();
        try {
            optPaciente = sPaciente.buscarPaciente(nSS);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
        return transformador.convertirADTOP(optPaciente.get());
    }

    @GetMapping("/{nSS}/citas")
    @ResponseBody
    public List<CitaDTO> buscarCitasPaciente(@PathVariable("nSS") String nSS) {
        List<CitaDTO> citasDtos = new ArrayList<>();
        try {
            List<Cita> citas = sCita.buscarXPaciente(nSS);
            if (citas != null) {
                citas.forEach(cita -> {
                    citasDtos.add(transformador.convertirADTOC(cita));
                });
            }
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
        return citasDtos;
    }

    @GetMapping("/{nSS}/informes")
    @ResponseBody
    public List<InformePacienteDTO> buscarInformesXPaciente(@PathVariable("nSS") String nSS) {
        List<Informe> listaInformes = new ArrayList<>();
        try {
            listaInformes = sInformes.buscarInformesXPaciente(nSS);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
        return listaInformes.stream().map(informe
                -> transformador.convertirADTOIP(informe)).collect(Collectors.toList());
    }

}
