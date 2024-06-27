package com.LuuQu.medicalclinic.service;

import com.LuuQu.medicalclinic.exception.AppointmentException;
import com.LuuQu.medicalclinic.exception.NotFoundException;
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
import com.LuuQu.medicalclinic.testHelper.TestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
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
    void addPatient_AppointmentDoesNotExist_NotFoundExceptionThrown() {
        //given
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());
        //when i then
        assertThatThrownBy(() -> appointmentService.addPatient(1L, 1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Appointment not found");
    }

    @Test
    void addPatient_AppointmentHavePatient_AppointmentExceptionThrown() {
        //given
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        Patient patient = new Patient();
        appointment.setPatient(patient);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        //when i then
        assertThatThrownBy(() -> appointmentService.addPatient(1L, 1L))
                .isInstanceOf(AppointmentException.class)
                .hasMessageContaining("Appointment already have patient");
    }

    @Test
    void addPatient_PatientNotFound_NotFoundExceptionThrown() {
        //given
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        //when i then
        assertThatThrownBy(() -> appointmentService.addPatient(1L, 1L))
                .isInstanceOf(NotFoundException.class)
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
    void addAppointment_IncorrectMinutesInStartDate_AppointmentExceptionThrown() {
        AppointmentDto appointmentDto = new AppointmentDto();
        LocalDateTime startTime = LocalDateTime.now().plusHours(1).withMinute(5);
        appointmentDto.setStartDate(startTime);

        //when i then
        assertThatThrownBy(() -> appointmentService.addAppointment(1L, appointmentDto))
                .isInstanceOf(AppointmentException.class)
                .hasMessageContaining("Invalid startDate time");
    }

    @Test
    void addAppointment_IncorrectMinutesInEndDate_AppointmentExceptionThrown() {
        AppointmentDto appointmentDto = new AppointmentDto();
        LocalDateTime startTime = LocalDateTime.now().plusHours(2).withMinute(0);
        appointmentDto.setStartDate(startTime);
        LocalDateTime endTime = LocalDateTime.now().plusHours(3).withMinute(5);
        appointmentDto.setEndDate(endTime);

        //when i then
        assertThatThrownBy(() -> appointmentService.addAppointment(1L, appointmentDto))
                .isInstanceOf(AppointmentException.class)
                .hasMessageContaining("Invalid endDate time");
    }

    @Test
    void addAppointment_startDateBeforeCurrent_AppointmentExceptionThrown() {
        AppointmentDto appointmentDto = new AppointmentDto();
        LocalDateTime startTime = LocalDateTime.now().minusDays(1).withMinute(0);
        appointmentDto.setStartDate(startTime);
        LocalDateTime endTime = LocalDateTime.now().plusDays(1).withMinute(0);
        appointmentDto.setEndDate(endTime);

        //when i then
        assertThatThrownBy(() -> appointmentService.addAppointment(1L, appointmentDto))
                .isInstanceOf(AppointmentException.class)
                .hasMessageContaining("Appointment should be after active date");
    }

    @Test
    void addAppointment_endDateBeforeStartDate_AppointmentExceptionThrown() {
        AppointmentDto appointmentDto = new AppointmentDto();
        LocalDateTime startTime = LocalDateTime.now().plusDays(1).withMinute(0);
        appointmentDto.setStartDate(startTime);
        LocalDateTime endTime = LocalDateTime.now().plusDays(1).minusHours(1).withMinute(0);
        appointmentDto.setEndDate(endTime);

        //when i then
        assertThatThrownBy(() -> appointmentService.addAppointment(1L, appointmentDto))
                .isInstanceOf(AppointmentException.class)
                .hasMessageContaining("Start date should be before end date");
    }

    @Test
    void addAppointment_endDateEqualsStartDate_AppointmentExceptionThrown() {
        AppointmentDto appointmentDto = new AppointmentDto();
        LocalDateTime time = LocalDateTime.now().plusDays(1).withMinute(0);
        appointmentDto.setStartDate(time);
        appointmentDto.setEndDate(time);

        //when i then
        assertThatThrownBy(() -> appointmentService.addAppointment(1L, appointmentDto))
                .isInstanceOf(AppointmentException.class)
                .hasMessageContaining("Start date and end date shouldn't be the same");
    }

    @Test
    void addAppointment_nonExistentDoctor_NotFoundExceptionThrown() {
        AppointmentDto appointmentDto = new AppointmentDto();
        LocalDateTime startTime = LocalDateTime.now().plusDays(1).withMinute(0);
        appointmentDto.setStartDate(startTime);
        LocalDateTime endTime = LocalDateTime.now().plusDays(1).plusHours(1).withMinute(0);
        appointmentDto.setEndDate(endTime);
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());
        //when i then
        assertThatThrownBy(() -> appointmentService.addAppointment(1L, appointmentDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Doctor doesn't exist");
    }

    @Test
    void addAppointment_doctorHaveAppointmentOnSetTime_AppointmentExceptionThrown() {
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
                .isInstanceOf(AppointmentException.class)
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

    @Test
    void getPatientAppointments_DataCorrect_DtoListReturned() {
        Long patientId = 1L;
        List<Appointment> appointmentList = TestData.AppointmentFactory.getList(2);
        for (Appointment appointment : appointmentList) {
            appointment.setPatient(TestData.PatientFactory.get(1L));
        }
        when(appointmentRepository.getDoctorAppointments(patientId)).thenReturn(appointmentList);
        List<AppointmentDto> expectedResult = TestData.AppointmentDtoFactory.getList(2);
        for (AppointmentDto appointment : expectedResult) {
            appointment.setPatient(TestData.PatientDtoFactory.get(1L));
        }

        List<AppointmentDto> result = appointmentService.getDoctorAppointments(patientId);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getDoctorAppointments_DataCorrect_DtoListReturned() {
        Long doctorId = 1L;
        List<Appointment> appointmentList = TestData.AppointmentFactory.getList(2);
        for (Appointment appointment : appointmentList) {
            appointment.setDoctor(TestData.DoctorFactory.get(1L));
        }
        when(appointmentRepository.getDoctorAppointments(doctorId)).thenReturn(appointmentList);
        List<AppointmentDto> expectedResult = TestData.AppointmentDtoFactory.getList(2);
        for (AppointmentDto appointment : expectedResult) {
            appointment.setDoctor(TestData.DoctorSimpleDtoFactory.get(1L));
        }

        List<AppointmentDto> result = appointmentService.getDoctorAppointments(doctorId);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getDoctorAvailableHours_DataCorrect_DtoListReturned() {
        Long doctorId = 1L;
        LocalDate date = LocalDate.now();
        String specialization = "specialization";
        List<Appointment> appointmentList = TestData.AppointmentFactory.getList(2);
        for (Appointment appointment : appointmentList) {
            appointment.setDoctor(TestData.DoctorFactory.get(1L));
        }
        when(appointmentRepository.getDoctorAppointments(doctorId, date, specialization)).thenReturn(appointmentList);
        List<AppointmentDto> expectedResult = TestData.AppointmentDtoFactory.getList(2);
        for (AppointmentDto appointment : expectedResult) {
            appointment.setDoctor(TestData.DoctorSimpleDtoFactory.get(1L));
        }

        List<AppointmentDto> result = appointmentService.getDoctorAvailableHours(date, specialization, doctorId);

        Assertions.assertEquals(expectedResult, result);
    }
}
