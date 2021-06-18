package com.davidechiarelli.taxcodeapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * DTO to manage users to use for tax code calculations
 */

@Data
@NoArgsConstructor
public class UserDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private Gender gender;
    @NotNull
    @Past
    private LocalDate dateOfBirth;
    @NotBlank
    @Pattern(regexp = "^[A-Za-z ,.'-]+$", message = "City of birth must contain only letters or these chars -> , - . ' and spaces")
    private String cityOfBirth;
    private String provinceOfBirth;

    public enum Gender{
        M, F
    }
}
