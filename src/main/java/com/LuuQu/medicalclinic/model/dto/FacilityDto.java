package com.LuuQu.medicalclinic.model.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class FacilityDto {
    private Long id;
    private String name;
    private String city;
    private String zipCode;
    private String street;
    private String buildingNo;
    private Set<DoctorSimpleDto> doctors = new HashSet<>();
}
