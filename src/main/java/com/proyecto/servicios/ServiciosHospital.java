/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.servicios;

import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.serviciosI.ServiciosHospitalI;
import com.proyecto.repositorios.HospitalRepository;
import com.proyecto.modelos.Hospital;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ck
 */
@Service("ServiciosHospitalI")
@Transactional
public class ServiciosHospital implements ServiciosHospitalI {

    @Autowired
    private HospitalRepository repositorioH;

    @Override
    public List<Hospital> buscarTodosH() {
        return repositorioH.findAll();
    }

    @Override
    public void save(Hospital hospital1) {
        repositorioH.save(hospital1);
    }

    @Override
    public void eliminarHospital(String nombre) throws ExcepcionServicio {
        Optional<Hospital> optHospital = repositorioH.findById(nombre);
        if (!optHospital.isPresent()) {
            throw new ExcepcionServicio("El hospital no existe");
        }
        repositorioH.deleteById(nombre);
    }

    @Override
    public Optional<Hospital> buscarHospital(String nombre) throws ExcepcionServicio {
        Optional<Hospital> optHospital = repositorioH.findById(nombre);
        if (!optHospital.isPresent()) {
            throw new ExcepcionServicio("El hospital no existe");
        }
        return optHospital;
    }
}
