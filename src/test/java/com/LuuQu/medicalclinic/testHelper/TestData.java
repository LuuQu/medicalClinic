package com.LuuQu.medicalclinic.testHelper;

import com.LuuQu.medicalclinic.model.dto.*;
import com.LuuQu.medicalclinic.model.entity.Appointment;
import com.LuuQu.medicalclinic.model.entity.Doctor;
import com.LuuQu.medicalclinic.model.entity.Facility;
import com.LuuQu.medicalclinic.model.entity.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestData {
    public static class FacilityFactory {

        public static List<Facility> getList(int amount) {
            List<Facility> list = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                list.add(get((long) i));
            }
            return list;
        }

        public static List<Facility> getList() {
            return getList(2);
        }

        public static Facility get(Long id) {
            Facility facility = get();
            facility.setId(id);
            return facility;
        }

        public static Facility get() {
            Facility facility = new Facility();
            facility.setName("Name");
            facility.setCity("City");
            facility.setZipCode("ZipCode");
            facility.setStreet("Street");
            facility.setBuildingNo("123");
            return facility;
        }
    }

    public static class FacilityDtoFactory {

        public static List<FacilityDto> getList(int amount) {
            List<FacilityDto> list = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                list.add(get((long) i));
            }
            return list;
        }

        public static List<FacilityDto> getList() {
            return getList(2);
        }

        public static FacilityDto get(Long id) {
            FacilityDto facilityDto = get();
            facilityDto.setId(id);
            return facilityDto;
        }

        public static FacilityDto get() {
            FacilityDto facilityDto = new FacilityDto();
            facilityDto.setName("Name");
            facilityDto.setCity("City");
            facilityDto.setZipCode("ZipCode");
            facilityDto.setStreet("Street");
            facilityDto.setBuildingNo("123");
            return facilityDto;
        }
    }

    public static class FacilitySimpleDtoFactory {

        public static List<FacilitySimpleDto> getList(int amount) {
            List<FacilitySimpleDto> list = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                list.add(get((long) i));
            }
            return list;
        }

        public static List<FacilitySimpleDto> getList() {
            return getList(2);
        }

        public static FacilitySimpleDto get(Long id) {
            FacilitySimpleDto facilitySimpleDto = get();
            facilitySimpleDto.setId(id);
            return facilitySimpleDto;
        }

        public static FacilitySimpleDto get() {
            FacilitySimpleDto facilitySimpleDto = new FacilitySimpleDto();
            facilitySimpleDto.setName("Name");
            facilitySimpleDto.setCity("City");
            facilitySimpleDto.setZipCode("ZipCode");
            facilitySimpleDto.setStreet("Street");
            facilitySimpleDto.setBuildingNo("123");
            return facilitySimpleDto;
        }
    }

    public static class PatientFactory {

        public static List<Patient> getList(int amount) {
            List<Patient> list = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                list.add(get((long) i));
            }
            return list;
        }

        public static List<Patient> getList() {
            return getList(2);
        }

        public static Patient get(Long id) {
            Patient patient = get();
            patient.setId(id);
            return patient;
        }

        public static Patient get() {
            Patient patient = new Patient();
            patient.getUser().setEmail("email");
            patient.getUser().setPassword("password");
            patient.setIdCardNo("idCardNo");
            patient.getUser().setFirstName("firstName");
            patient.getUser().setLastName("lastName");
            patient.setPhoneNumber("123123123");
            patient.setBirthday(LocalDate.of(2000, 1, 1));
            return patient;
        }
    }

    public static class PatientDtoFactory {

        public static List<PatientDto> getList(int amount) {
            List<PatientDto> list = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                list.add(get((long) i));
            }
            return list;
        }

        public static List<PatientDto> getList() {
            return getList(2);
        }

        public static PatientDto get(Long id) {
            PatientDto patientDto = get();
            patientDto.setId(id);
            return patientDto;
        }

        public static PatientDto get() {
            PatientDto patientDto = new PatientDto();
            patientDto.setEmail("email");
            patientDto.setPassword("password");
            patientDto.setIdCardNo("idCardNo");
            patientDto.setFirstName("firstName");
            patientDto.setLastName("lastName");
            patientDto.setPhoneNumber("123123123");
            patientDto.setBirthday(LocalDate.of(2000, 1, 1));
            return patientDto;
        }
    }

    public static class DoctorFactory {

        public static List<Doctor> getList(int amount) {
            List<Doctor> list = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                list.add(get((long) i));
            }
            return list;
        }

        public static List<Doctor> getList() {
            return getList(2);
        }

        public static Doctor get(Long id) {
            Doctor doctor = get();
            doctor.setId(id);
            return doctor;
        }

        public static Doctor get() {
            Doctor doctor = new Doctor();
            doctor.getUser().setEmail("email");
            doctor.getUser().setPassword("password");
            doctor.getUser().setFirstName("firstName");
            doctor.getUser().setLastName("lastName");
            doctor.setSpecialization("specialization");
            return doctor;
        }
    }

    public static class DoctorDtoFactory {

        public static List<DoctorDto> getList(int amount) {
            List<DoctorDto> list = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                list.add(get((long) i));
            }
            return list;
        }

        public static List<DoctorDto> getList() {
            return getList(2);
        }

        public static DoctorDto get(Long id) {
            DoctorDto doctorDto = get();
            doctorDto.setId(id);
            return doctorDto;
        }

        public static DoctorDto get() {
            DoctorDto doctorDto = new DoctorDto();
            doctorDto.setEmail("email");
            doctorDto.setPassword("password");
            doctorDto.setFirstName("firstName");
            doctorDto.setLastName("lastName");
            doctorDto.setSpecialization("specialization");
            return doctorDto;
        }
    }

    public static class DoctorSimpleDtoFactory {

        public static List<DoctorSimpleDto> getList(int amount) {
            List<DoctorSimpleDto> list = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                list.add(get((long) i));
            }
            return list;
        }

        public static List<DoctorSimpleDto> getList() {
            return getList(2);
        }

        public static DoctorSimpleDto get(Long id) {
            DoctorSimpleDto doctorSimpleDto = get();
            doctorSimpleDto.setId(id);
            return doctorSimpleDto;
        }

        public static DoctorSimpleDto get() {
            DoctorSimpleDto doctorSimpleDto = new DoctorSimpleDto();
            doctorSimpleDto.setEmail("email");
            doctorSimpleDto.setPassword("password");
            doctorSimpleDto.setFirstName("firstName");
            doctorSimpleDto.setLastName("lastName");
            doctorSimpleDto.setSpecialization("specialization");
            return doctorSimpleDto;
        }
    }

    public static class AppointmentDtoFactory {

        public static List<AppointmentDto> getList(int amount) {
            List<AppointmentDto> list = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                list.add(get((long) i));
            }
            return list;
        }

        public static List<AppointmentDto> getList() {
            return getList(2);
        }

        public static AppointmentDto get(Long id) {
            AppointmentDto appointmentDto = get();
            appointmentDto.setId(id);
            return appointmentDto;
        }

        public static AppointmentDto get() {
            AppointmentDto appointmentDto = new AppointmentDto();
            appointmentDto.setStartDate(LocalDateTime.now().plusDays(1).withMinute(0).withSecond(0).withNano(0));
            appointmentDto.setEndDate(LocalDateTime.now().plusDays(1).plusHours(1).withMinute(0).withSecond(0).withNano(0));
            return appointmentDto;
        }
    }

    public static class AppointmentFactory {

        public static List<Appointment> getList(int amount) {
            List<Appointment> list = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                list.add(get((long) i));
            }
            return list;
        }

        public static List<Appointment> getList() {
            return getList(2);
        }

        public static Appointment get(Long id) {
            Appointment appointment = get();
            appointment.setId(id);
            return appointment;
        }

        public static Appointment get() {
            Appointment appointment = new Appointment();
            appointment.setStartDate(LocalDateTime.now().plusDays(1).withMinute(0).withSecond(0).withNano(0));
            appointment.setEndDate(LocalDateTime.now().plusDays(1).plusHours(1).withMinute(0).withSecond(0).withNano(0));
            return appointment;
        }
    }
}
