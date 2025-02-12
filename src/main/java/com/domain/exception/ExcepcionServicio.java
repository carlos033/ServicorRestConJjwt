/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author ck
 */
public class ExcepcionServicio extends ResponseStatusException {

	private static final long serialVersionUID = 7L;

	public ExcepcionServicio(HttpStatus status, String msg) {
		super(status, "Ha ocurrido una excepcion en el servicio: " + msg);
	}
}
