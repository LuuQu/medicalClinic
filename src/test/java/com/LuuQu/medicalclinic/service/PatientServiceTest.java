package com.LuuQu.medicalclinic.service;

import com.LuuQu.medicalclinic.testHelper.TestData;
import com.LuuQu.medicalclinic.mapper.PatientMapper;
import com.LuuQu.medicalclinic.model.dto.PatientDto;
import com.LuuQu.medicalclinic.model.entity.Patient;
import com.LuuQu.medicalclinic.model.entity.User;
import com.LuuQu.medicalclinic.repository.PatientRepository;
import com.LuuQu.medicalclinic.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class PatientServiceTest {
    private PatientService patientService;
    private PatientRepository patientRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        this.patientRepository = Mockito.mock(PatientRepository.class);
        this.userRepository = Mockito.mock(UserRepository.class);
        PatientMapper patientMapper = Mappers.getMapper(PatientMapper.class);
        this.patientService = new PatientService(patientRepository, userRepository, patientMapper);
    }

    @Test
    void getPatients_getDoctorList_dtoListReturned() {
        List<Patient> list = TestData.PatientFactory.getList();
        Pageable pageable = PageRequest.of(0, 10);
        when(patientRepository.findAll(pageable)).thenReturn(new PageImpl<>(list));
        List<PatientDto> expectedResult = TestData.PatientDtoFactory.getList();

        List<PatientDto> result = patientService.getPatients(pageable);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getPatient_noPatientInDb_CorrectException() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> patientService.getPatient(1L));

        Assertions.assertEquals(exception.getMessage(), "Patient does not exist");
    }

    @Test
    void getPatient_patientFound_DtoReturned() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(TestData.PatientFactory.get(1L)));
        PatientDto expectedResult = TestData.PatientDtoFactory.get(1L);
        expectedResult.setId(1L);

        PatientDto result = patientService.getPatient(1L);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void addPatient_emailTaken_CorrectException() {
        String email = "email";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        PatientDto patientDto = new PatientDto();
        patientDto.setEmail(email);

        var exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> patientService.addPatient(patientDto));

        Assertions.assertEquals(exception.getMessage(), "User with given e-mail already exist");
    }

    @Test
    void addPatient_patientAdded_DtoReturned() {
        String email = "email";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        PatientDto patientDto = new PatientDto();
        patientDto.setEmail(email);
        PatientDto expectedResult = new PatientDto();
        expectedResult.setEmail(email);

        PatientDto result = patientService.addPatient(patientDto);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void deletePatient_patientInDb_PatientDeleted() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(new Patient()));

        patientService.deletePatient(1L);

        verify(patientRepository, times(1)).delete(new Patient());
    }

    @Test
    void deletePatient_noPatientInDb_functionReturned() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        patientService.deletePatient(1L);

        verify(patientRepository, times(0)).delete(new Patient());
    }

    @Test
    void editPatient_noPatientInDb_CorrectException() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> patientService.editPatient(1L, new PatientDto()));

        Assertions.assertEquals(exception.getMessage(), "Patient does not exist");
    }

    @Test
    void editPatient_patientInDb_DtoReturned() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(TestData.PatientFactory.get(1L)));
        PatientDto patientDto = TestData.PatientDtoFactory.get();
        PatientDto expectedResult = TestData.PatientDtoFactory.get(1L);

        PatientDto result = patientService.editPatient(1L, patientDto);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void editPassword_EmptyPassword_CorrectException() {
        var exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> patientService.editPassword(1L, new PatientDto()));

        Assertions.assertEquals(exception.getMessage(), "Incorrect body");
    }

    @Test
    void editPassword_noPatientInDb_CorrectException() {
        String password = "password";
        PatientDto patientPassword = new PatientDto();
        patientPassword.setPassword(password);
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> patientService.editPassword(1L, patientPassword));

        Assertions.assertEquals(exception.getMessage(), "Patient does not exist");
    }

    @Test
    void editPassword_correctApproach_DtoReturned() {
        String password = "password";
        PatientDto patientPassword = new PatientDto();
        patientPassword.setPassword(password);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(TestData.PatientFactory.get(1L)));
        PatientDto expectedResult = TestData.PatientDtoFactory.get(1L);
        expectedResult.setPassword(password);
        expectedResult.setId(1L);

        PatientDto result = patientService.editPassword(1L, patientPassword);

        Assertions.assertEquals(expectedResult, result);
    }
}
