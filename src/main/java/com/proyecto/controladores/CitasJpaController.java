/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.proyecto.dto.MedicoDTO;
import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Cita;
import com.proyecto.modelos.Medico;
import com.proyecto.serviciosI.ServiciosCitaI;
import com.proyecto.utiles.Transformadores;

import jakarta.validation.Valid;

/**
 *
 * @author ck
 */
@RestController
@RequestMapping("/citas")
public class CitasJpaController {

    @Autowired
    private Transformadores transformador;
    @Autowired
    private ServiciosCitaI sCita;

    @GetMapping()
    @ResponseBody
    public List<CitaDTO> listarCitas() {
        List<Cita> listaCitas = new ArrayList<>();
        listaCitas = sCita.buscarTodasC();
        return listaCitas.stream().map(cita -> transformador.convertirADTOC(cita)).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public CitaDTO aniadirCita(@Valid @RequestBody CitaDTO citaDto) {
        Cita convertedCita = transformador.convertirAEntidadC(citaDto);
        CitaDTO resultado;
        try {
            sCita.crearCita(convertedCita);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
        resultado = transformador.convertirADTOC(convertedCita);
        return resultado;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarCita(@PathVariable("id") int id) {
        try {
            sCita.eliminarCita(id);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
    }

    @GetMapping("/{nSS}/buscarMMedico")
    @ResponseBody
    public MedicoDTO buscarMiMedico(@PathVariable("nSS") String nSS) {
        MedicoDTO dto = null;
        Medico m = null;
        try {
            m = sCita.buscarMiMedico(nSS);
            dto = transformador.convertirADTOM(m);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
        return dto;
    }

    @GetMapping("/{id}/buscarXId")
    @ResponseBody
    public CitaDTO buscarXId(@PathVariable("id") int id) {
        CitaDTO dto = null;
        Cita cita = null;
        try {
            cita = sCita.buscarXId(id);
            dto = transformador.convertirADTOC(cita);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
        return dto;
    }
}
