/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ck
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InformeDTO implements Serializable {

	private static final long serialVersionUID = 8L;
	protected String url;
	protected String nombreInf;

}
