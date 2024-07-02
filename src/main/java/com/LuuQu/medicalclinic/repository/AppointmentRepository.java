package com.LuuQu.medicalclinic.repository;

import com.LuuQu.medicalclinic.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query(
            value = "SELECT * FROM APPOINTMENT u WHERE u.doctor_id = ?1" +
                    " AND u.start_date < ?3" +
                    " AND u.end_date > ?2",
            nativeQuery = true)
    List<Appointment> getBetweenTime(Long doctorId, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT * FROM APPOINTMENT a " +
            "WHERE a.patient_id = ?1", nativeQuery = true)
    List<Appointment> getPatientAppointments(Long patientId);

    @Query(value = "SELECT * FROM APPOINTMENT a " +
            "WHERE a.doctor_id = ?1", nativeQuery = true)
    List<Appointment> getDoctorAppointments(Long doctorId);

    @Query("SELECT a FROM Appointment a JOIN a.doctor d " +
            "WHERE a.doctor.id = ?1 " +
            "AND DATE(a.startDate) = ?2 " +
            "AND d.specialization = ?3")
    List<Appointment> getDoctorAppointments(Long doctorId, LocalDate date, String specialization);
}
