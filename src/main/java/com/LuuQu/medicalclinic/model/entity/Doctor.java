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
@Table(name = "DOCTOR")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "SPECIALIZATION")
    private String specialization;
    @ManyToMany
    @JoinTable(
            name = "Doctor_Facility",
            joinColumns = {@JoinColumn(name = "doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "facility_id")}
    )
    private Set<Facility> facilities = new HashSet<>();

    public void update(Doctor doctor) {
        this.email = doctor.getEmail();
        this.password = doctor.getPassword();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.specialization = doctor.getSpecialization();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Doctor other)) {
            return false;
        }

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialization='" + specialization + '\'' +
                ", facilities=" + facilities.stream()
                .map(Facility::toSimpleString)
                .collect(Collectors.joining(", ", "[", "]")) +
                '}';
    }

    public String toSimpleString() {
        return "Doctor{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
