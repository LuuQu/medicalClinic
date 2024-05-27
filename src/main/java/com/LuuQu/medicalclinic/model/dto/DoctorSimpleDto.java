package com.LuuQu.medicalclinic.model.dto;

import lombok.Data;

@Data
public class DoctorSimpleDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String specialization;
}
