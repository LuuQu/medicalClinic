package com.LuuQu.medicalclinic.repository;

import com.LuuQu.medicalclinic.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientRepository extends JpaRepository<Patient,Long> {
    Patient findByEmail(String email);
}
