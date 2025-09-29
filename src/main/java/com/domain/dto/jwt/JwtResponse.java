/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.domain.dto.jwt;

import java.io.Serializable;

/**
 *
 * @author ck
 */
public record JwtResponse(String jwttoken) implements Serializable {

}
