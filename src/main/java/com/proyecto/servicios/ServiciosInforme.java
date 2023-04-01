/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.servicios;

import com.proyecto.excepciones.ExcepcionServicio;
import com.proyecto.serviciosI.ServiciosInformeI;
import com.proyecto.modelos.Informe;
import com.proyecto.modelos.Medico;
import com.proyecto.modelos.Paciente;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyecto.repositorios.InformeRepository;
import com.proyecto.repositorios.MedicoRepository;
import com.proyecto.repositorios.PacienteRepository;
import java.util.Optional;

/**
 *
 * @author ck
 */
@Service("ServiciosInformesI")
@Transactional
public class ServiciosInforme implements ServiciosInformeI {

    @Autowired
    private InformeRepository repositorioI;
    @Autowired
    private PacienteRepository repositorioP;
    @Autowired
    private MedicoRepository repositorioM;

    @Override
    public List<Informe> buscarTodosI() {
        return repositorioI.findAll();
    }

    @Override
    public void saveInformes(Informe informe) throws ExcepcionServicio {
        repositorioI.save(informe);
    }

    @Override
    public void eliminarInforme(String nombre) throws ExcepcionServicio {
        Optional<Informe> optInformes = repositorioI.findById(nombre);
        if (!optInformes.isPresent()) {
            throw new ExcepcionServicio("El nombre del informe no existe");
        }
        repositorioI.deleteById(nombre);
    }

    @Override
    public List<Informe> buscarInformesXPaciente(String nSS) throws ExcepcionServicio {
        Optional<Paciente> optPaciente = repositorioP.findById(nSS);
        if (!optPaciente.isPresent()) {
            throw new ExcepcionServicio("El numero de SS no existe");
        }
        return repositorioI.buscarInformeXPaciente(nSS);
    }

    @Override
    public List<Informe> buscarInformesXMedico(String nLicencia) throws ExcepcionServicio {
        Optional<Medico> optMedico = repositorioM.findById(nLicencia);
        if (!optMedico.isPresent()) {
            throw new ExcepcionServicio("El numero de Licencia no existe");
        }
        return repositorioI.buscarInformeXMedico(nLicencia);
    }

    @Override
    public void eliminarTodosXPaciente(String nSS) throws ExcepcionServicio {
        List<Informe> listaCitas = buscarInformesXPaciente(nSS);
        repositorioI.deleteInBatch(listaCitas);
    }

    @Override
    public Informe crearInforme(Informe informe) throws ExcepcionServicio {
        Optional<Medico> m = repositorioM.findById(informe.getMedico().getnLicencia());
        Optional<Paciente> p = repositorioP.findById(informe.getPaciente().getnSS());
        if (!m.isPresent()) {
            throw new ExcepcionServicio("El numero de licencia no existe");
        }
        if (!p.isPresent()) {
            throw new ExcepcionServicio("El numero de SS no existe");
        }
        informe.setPaciente(p.get());
        informe.setMedico(m.get());
        return this.repositorioI.save(informe);
    }
}
