package com.LuuQu.medicalclinic.service;

import com.LuuQu.medicalclinic.mapper.AppointmentMapper;
import com.LuuQu.medicalclinic.mapper.DoctorMapper;
import com.LuuQu.medicalclinic.mapper.PatientMapper;
import com.LuuQu.medicalclinic.model.dto.AppointmentDto;
import com.LuuQu.medicalclinic.model.dto.DoctorSimpleDto;
import com.LuuQu.medicalclinic.model.dto.PatientDto;
import com.LuuQu.medicalclinic.model.entity.Appointment;
import com.LuuQu.medicalclinic.model.entity.Doctor;
import com.LuuQu.medicalclinic.model.entity.Patient;
import com.LuuQu.medicalclinic.repository.AppointmentRepository;
import com.LuuQu.medicalclinic.repository.DoctorRepository;
import com.LuuQu.medicalclinic.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class AppointmentServiceTest {
    private AppointmentService appointmentService;
    private AppointmentRepository appointmentRepository;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;

    @BeforeEach
    void setUp() {
        this.appointmentRepository = Mockito.mock(AppointmentRepository.class);
        this.patientRepository = Mockito.mock(PatientRepository.class);
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        AppointmentMapper appointmentMapper = Mappers.getMapper(AppointmentMapper.class);
        ReflectionTestUtils.setField(appointmentMapper, "doctorMapper", Mappers.getMapper(DoctorMapper.class));
        ReflectionTestUtils.setField(appointmentMapper, "patientMapper", Mappers.getMapper(PatientMapper.class));
        this.appointmentService = new AppointmentService(appointmentRepository, patientRepository, doctorRepository, appointmentMapper);
    }

    @Test
    void addPatient_AppointmentDoesNotExist_CorrectException() {
        //given
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());
        //when i then
        assertThatThrownBy(() -> appointmentService.addPatient(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Appointment not found");
    }

    @Test
    void addPatient_AppointmentHavePatient_CorrectException() {
        //given
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        Patient patient = new Patient();
        appointment.setPatient(patient);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        //when i then
        assertThatThrownBy(() -> appointmentService.addPatient(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Appointment already have patient");
    }

    @Test
    void addPatient_PatientNotFound_CorrectException() {
        //given
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        //when i then
        assertThatThrownBy(() -> appointmentService.addPatient(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Patient not found");
    }

    @Test
    void addPatient_PatientAdded_ReturnAppointmentDto() {
        //given
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        Patient patient = new Patient();
        patient.setId(1L);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(1L);
        PatientDto patientDto = new PatientDto();
        patientDto.setId(1L);
        appointmentDto.setPatient(patientDto);

        //when
        AppointmentDto result = appointmentService.addPatient(1L, 1L);

        //then
        assertThat(result).isEqualTo(appointmentDto);
    }

    @Test
    void addAppointment_IncorrectMinutesInStartDate_CorrectException() {
        AppointmentDto appointmentDto = new AppointmentDto();
        LocalDateTime startTime = LocalDateTime.now().plusHours(1).withMinute(5);
        appointmentDto.setStartDate(startTime);

        //when i then
        assertThatThrownBy(() -> appointmentService.addAppointment(1L, appointmentDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid startDate time");
    }

    @Test
    void addAppointment_IncorrectMinutesInEndDate_CorrectException() {
        AppointmentDto appointmentDto = new AppointmentDto();
        LocalDateTime startTime = LocalDateTime.now().plusHours(2).withMinute(0);
        appointmentDto.setStartDate(startTime);
        LocalDateTime endTime = LocalDateTime.now().plusHours(3).withMinute(5);
        appointmentDto.setEndDate(endTime);

        //when i then
        assertThatThrownBy(() -> appointmentService.addAppointment(1L, appointmentDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid endDate time");
    }

    @Test
    void addAppointment_startDateBeforeCurrent_CorrectException() {
        AppointmentDto appointmentDto = new AppointmentDto();
        LocalDateTime startTime = LocalDateTime.now().minusDays(1).withMinute(0);
        appointmentDto.setStartDate(startTime);
        LocalDateTime endTime = LocalDateTime.now().plusDays(1).withMinute(0);
        appointmentDto.setEndDate(endTime);

        //when i then
        assertThatThrownBy(() -> appointmentService.addAppointment(1L, appointmentDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Appointment should be after active date");
    }

    @Test
    void addAppointment_endDateBeforeStartDate_CorrectException() {
        AppointmentDto appointmentDto = new AppointmentDto();
        LocalDateTime startTime = LocalDateTime.now().plusDays(1).withMinute(0);
        appointmentDto.setStartDate(startTime);
        LocalDateTime endTime = LocalDateTime.now().plusDays(1).minusHours(1).withMinute(0);
        appointmentDto.setEndDate(endTime);

        //when i then
        assertThatThrownBy(() -> appointmentService.addAppointment(1L, appointmentDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Start date should be before end date");
    }

    @Test
    void addAppointment_endDateEqualsStartDate_CorrectException() {
        AppointmentDto appointmentDto = new AppointmentDto();
        LocalDateTime startTime = LocalDateTime.now().plusDays(1).withMinute(0);
        appointmentDto.setStartDate(startTime);
        LocalDateTime endTime = LocalDateTime.now().plusDays(1).withMinute(0);
        appointmentDto.setEndDate(endTime);

        //when i then
        assertThatThrownBy(() -> appointmentService.addAppointment(1L, appointmentDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Start date and end date shouldn't be the same");
    }

    @Test
    void addAppointment_nonExistentDoctor_CorrectException() {
        AppointmentDto appointmentDto = new AppointmentDto();
        LocalDateTime startTime = LocalDateTime.now().plusDays(1).withMinute(0);
        appointmentDto.setStartDate(startTime);
        LocalDateTime endTime = LocalDateTime.now().plusDays(1).plusHours(1).withMinute(0);
        appointmentDto.setEndDate(endTime);
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());
        //when i then
        assertThatThrownBy(() -> appointmentService.addAppointment(1L, appointmentDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Doctor doesn't exist");
    }

    @Test
    void addAppointment_doctorHaveAppointmentOnSetTime_CorrectException() {
        AppointmentDto appointmentDto = new AppointmentDto();
        LocalDateTime startTime = LocalDateTime.now().plusDays(1).withMinute(0);
        appointmentDto.setStartDate(startTime);
        LocalDateTime endTime = LocalDateTime.now().plusDays(1).plusHours(1).withMinute(0);
        appointmentDto.setEndDate(endTime);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(new Doctor()));
        List<Appointment> list = new ArrayList<>();
        list.add(new Appointment());
        list.add(new Appointment());
        when(appointmentRepository.getBetweenTime(1L, startTime, endTime)).thenReturn(list);
        //when i then
        assertThatThrownBy(() -> appointmentService.addAppointment(1L, appointmentDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Doctor already have appointment on set time");
    }

    @Test
    void addAppointment_AppointmentAdded_ReturnAppointmentDto() {
        AppointmentDto appointmentDto = new AppointmentDto();
        LocalDateTime startTime = LocalDateTime.now().plusDays(1).withMinute(0);
        appointmentDto.setStartDate(startTime);
        LocalDateTime endTime = LocalDateTime.now().plusDays(1).plusHours(1).withMinute(0);
        appointmentDto.setEndDate(endTime);
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.getBetweenTime(1L, startTime, endTime)).thenReturn(new ArrayList<>());
        AppointmentDto expectedResult = new AppointmentDto();
        expectedResult.setStartDate(startTime);
        expectedResult.setEndDate(endTime);
        DoctorSimpleDto doctorSimpleDto = new DoctorSimpleDto();
        doctorSimpleDto.setId(1L);
        expectedResult.setDoctor(doctorSimpleDto);

        AppointmentDto result = appointmentService.addAppointment(1L, appointmentDto);

        assertThat(result).isEqualTo(expectedResult);
    }
}
