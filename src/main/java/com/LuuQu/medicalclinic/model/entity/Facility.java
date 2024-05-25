package com.LuuQu.medicalclinic.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
@Table(name = "FACILITY")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CITY")
    private String city;
    @Column(name = "ZIP_CODE")
    private String zipCode;
    @Column(name = "STREET")
    private String street;
    @Column(name = "BUILDING_NO")
    private String buildingNo;

    public void update(Facility facility) {
        this.name = facility.getName();
        this.city = facility.getCity();
        this.zipCode = facility.getZipCode();
        this.street = facility.getStreet();
        this.buildingNo = facility.getBuildingNo();
    }
}
