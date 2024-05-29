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
    @OneToOne(cascade = CascadeType.ALL)
    private User user = new User();
    @Column(name = "SPECIALIZATION")
    private String specialization;
    @ManyToMany
    @JoinTable(
            name = "Doctor_Facility",
            joinColumns = {@JoinColumn(name = "doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "facility_id")}
    )
    private Set<Facility> facilities = new HashSet<>();
    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointments = new HashSet<>();

    public void update(Doctor doctor) {
        this.user = doctor.getUser();
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
                ", user='" + user + '\'' +
                ", specialization='" + specialization + '\'' +
                ", facilities=" + facilities.stream()
                .map(Facility::toSimpleString)
                .collect(Collectors.joining(", ", "[", "]")) +
                '}';
    }

    public String toSimpleString() {
        return "Doctor{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
