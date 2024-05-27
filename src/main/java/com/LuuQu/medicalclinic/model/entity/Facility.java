package com.LuuQu.medicalclinic.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@Setter
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
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name = "Doctor_Facility",
            joinColumns = {@JoinColumn(name = "facility_id")},
            inverseJoinColumns = {@JoinColumn(name = "doctor_id")}
    )
    private Set<Doctor> doctors = new HashSet<>();

    public void update(Facility facility) {
        this.name = facility.getName();
        this.city = facility.getCity();
        this.zipCode = facility.getZipCode();
        this.street = facility.getStreet();
        this.buildingNo = facility.getBuildingNo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Facility other))
            return false;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Facility{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", street='" + street + '\'' +
                ", buildingNo='" + buildingNo + '\'' +
                ", doctors=" + doctors.stream()
                .map(Doctor::toSimpleString)
                .collect(Collectors.joining(", ", "[", "]")) +
                '}';
    }

    public String toSimpleString() {
        return "Facility{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", street='" + street + '\'' +
                ", buildingNo='" + buildingNo + '\'' +
                ", doctors='[...]'" + 
                '}';
    }
}
