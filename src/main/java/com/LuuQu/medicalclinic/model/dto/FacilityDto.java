package com.LuuQu.medicalclinic.model.dto;

import lombok.Data;

@Data
public class FacilityDto {
    private Long id;
    private String name;
    private String city;
    private String zipCode;
    private String street;
    private String buildingNo;
}
