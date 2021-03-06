package com.davidechiarelli.taxcodeapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * DTO to manage user accounts for security
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
