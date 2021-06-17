package com.davidechiarelli.taxcodeapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserDTO {
    @NotBlank
    @Pattern(regexp = "^[A-Za-z ,.'-]+$", message = "First name must contain only letters or these chars -> , - . ' and spaces")
    private String firstName;
    @NotBlank
    @Pattern(regexp = "^[A-Za-z ,.'-]+$", message = "Last name must contain only letters or these chars -> , - . ' and spaces")
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
