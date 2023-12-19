/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.controladores;

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

import com.proyecto.dto.InformeCompletoDTO;
import com.proyecto.dto.InformeDTO;
import com.proyecto.dto.InformeMedicoDTO;
import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Informe;
import com.proyecto.serviciosI.ServiciosInformeI;
import com.proyecto.utiles.Transformadores;

import jakarta.validation.Valid;

/**
 *
 * @author ck
 */
@RestController
@RequestMapping(path = "/informes")
public class InformesJpaController {

    @Autowired
    private Transformadores transformador;
    @Autowired
    private ServiciosInformeI sInformes;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public InformeMedicoDTO aniadirInforme(@Valid @RequestBody InformeCompletoDTO informeDto) {
        InformeMedicoDTO resultado;
        Informe convertedInforme = transformador.convertirAEntidadI(informeDto);
        try {
            sInformes.crearInforme(convertedInforme);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        resultado = transformador.convertirADTOIM(convertedInforme);
        return resultado;
    }

    @DeleteMapping("/{nombre}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarInforme(@PathVariable("nombre") String nombre) {
        try {
            sInformes.eliminarInforme(nombre);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping
    @ResponseBody
    public List<InformeDTO> listInformes() {
        List<Informe> listaInformes = sInformes.buscarTodosI();
        return listaInformes.stream().map(informe
                -> transformador.convertirADTOI(informe)).collect(Collectors.toList());
    }

}
