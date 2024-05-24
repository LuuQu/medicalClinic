package com.LuuQu.medicalclinic.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "APPOINTMENT")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "DATE")
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
