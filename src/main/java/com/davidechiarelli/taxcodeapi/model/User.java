package com.davidechiarelli.taxcodeapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Model class for Users
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dateOfBirth;
    private String cityOfBirth;
    private String provinceOfBirth;
}
