/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.infrastructure.controller;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.service.ServicioMedico;
import com.domain.dto.MedicoDTO;
import com.domain.exception.ExcepcionServicio;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@RestController
@RequestMapping(path = "/medicos")
public class MedicoJpaController {

  private final ServicioMedico sMedico;

  @PostMapping
  public ResponseEntity<Void> aniadirMedico(@Valid @RequestBody MedicoDTO medicoDTO) {
    String nLicencia = sMedico.saveMedico(medicoDTO);
    URI location = URI.create("/medicos/" + nLicencia);
    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("/{nLicencia}")
  public ResponseEntity<Void> eliminarMedico(@PathVariable String nLicencia) {
    sMedico.eliminarMedico(nLicencia);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{nLicencia}")
  public ResponseEntity<MedicoDTO> buscarMedico(@PathVariable String nLicencia) {
    MedicoDTO dto = sMedico.buscarMedico(nLicencia);
    if (dto == null) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(dto);
  }

  @GetMapping
  public ResponseEntity<List<MedicoDTO>> listMedicos(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok(sMedico.buscarTodosM(pageable));
  }

  @GetMapping("/especialidad/{especialidad}/hospital/{idHospital}")
  public ResponseEntity<List<MedicoDTO>> buscarMedicoXEspecialidad(
      @PathVariable String especialidad, @PathVariable long idHospital) {
    return ResponseEntity.ok(sMedico.buscarMedicoXEspecialidad(especialidad, idHospital));
  }

  @GetMapping("/hospital/{idHospital}")
  public ResponseEntity<Page<MedicoDTO>> buscarMedicosXHospital(@PathVariable long idHospital,
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok(sMedico.buscarMedicosXHospital(idHospital, pageable));
  }

  @GetMapping("/cabecera/{nSS}")
  public ResponseEntity<MedicoDTO> buscarMiMedico(@PathVariable String nSS)
      throws ExcepcionServicio {
    return ResponseEntity.ok(sMedico.buscarMiMedico(nSS));
  }
}
