package com.davidechiarelli.taxcodeapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxCodeDTO {
    @NotBlank
    @Size(min = 16, max = 16)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Tax code must be an alphanumeric value")
    private String taxCode;
}
