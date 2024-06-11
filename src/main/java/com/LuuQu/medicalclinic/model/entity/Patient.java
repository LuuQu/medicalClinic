package com.LuuQu.medicalclinic.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
@Table(name = "PATIENT")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ID_CARD_NO")
    private String idCardNo;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "BIRTHDAY")
    private LocalDate birthday;
    @OneToOne(cascade = CascadeType.ALL)
    private User user = new User();
    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointments = new HashSet<>();

    public void update(Patient patient) {
        this.user = patient.getUser();
        this.idCardNo = patient.getIdCardNo();
        this.phoneNumber = patient.getPhoneNumber();
        this.birthday = patient.getBirthday();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Patient other)) {
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
        return "Patient{" +
                "id=" + id +
                ", idCardNo='" + idCardNo + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthday=" + birthday +
                ", user=" + user +
                '}';
    }
}
