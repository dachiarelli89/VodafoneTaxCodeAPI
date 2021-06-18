package com.davidechiarelli.taxcodeapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO to manage JWT token
 */

@Data
@AllArgsConstructor
public class TokenDTO {
    private String token;
}
