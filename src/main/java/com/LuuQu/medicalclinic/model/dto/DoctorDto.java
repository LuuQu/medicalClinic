package com.LuuQu.medicalclinic.model.dto;

import com.LuuQu.medicalclinic.model.entity.Facility;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class DoctorDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String specialization;
    private Set<Facility> facilities = new HashSet<>();
}
