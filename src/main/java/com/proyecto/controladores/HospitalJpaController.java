/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.controladores;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto.dto.HospitalDTO;
import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.modelos.Hospital;
import com.proyecto.serviciosI.ServiciosHospitalI;
import com.proyecto.utiles.Transformadores;

import jakarta.validation.Valid;

/**
 *
 * @author ck
 */
@RestController
@RequestMapping(path = "/hospitales")
public class HospitalJpaController {

    @Autowired
    private Transformadores transformador;
    @Autowired
    private ServiciosHospitalI sHospital;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public HospitalDTO aniadirHospital(@Valid @RequestBody HospitalDTO hospitalDto) {
        Hospital hospital1 = transformador.convertirAEntidadH(hospitalDto);
        sHospital.save(hospital1);
        HospitalDTO resultado = transformador.convertirADTOH(hospital1);
        return resultado;
    }

    @DeleteMapping("/{nombre}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarHospital(@PathVariable("nombre") String nombreHos) {
        try {
            sHospital.eliminarHospital(nombreHos);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hospital no encontrado");
        }
    }

    @GetMapping()
    @ResponseBody
    public List<HospitalDTO> listarhospitales() {
        List<Hospital> listaHospital = sHospital.buscarTodosH();
        return listaHospital.stream().map(hospital -> transformador.convertirADTOH(hospital)).collect(Collectors.toList());
    }

    @GetMapping("{nombre}")
    @ResponseBody
    public HospitalDTO buscarHospital(@PathVariable("nombre") String nombre) throws ExcepcionServicio {
        Optional<Hospital> optHospital = sHospital.buscarHospital(nombre);
        if (!optHospital.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hospital no encontrado");
        }
        return transformador.convertirADTOH(optHospital.get());
    }
}
