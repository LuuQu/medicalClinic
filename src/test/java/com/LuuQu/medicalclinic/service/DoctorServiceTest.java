package com.LuuQu.medicalclinic.service;

import com.LuuQu.medicalclinic.exception.NotFoundException;
import com.LuuQu.medicalclinic.testHelper.TestData;
import com.LuuQu.medicalclinic.mapper.DoctorMapper;
import com.LuuQu.medicalclinic.model.dto.DoctorDto;
import com.LuuQu.medicalclinic.model.dto.FacilitySimpleDto;
import com.LuuQu.medicalclinic.model.entity.Doctor;
import com.LuuQu.medicalclinic.model.entity.Facility;
import com.LuuQu.medicalclinic.repository.DoctorRepository;
import com.LuuQu.medicalclinic.repository.FacilityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

public class DoctorServiceTest {
    private DoctorService doctorService;
    private DoctorRepository doctorRepository;
    private FacilityRepository facilityRepository;

    @BeforeEach
    void setUp() {
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.facilityRepository = Mockito.mock(FacilityRepository.class);
        DoctorMapper doctorMapper = Mappers.getMapper(DoctorMapper.class);
        doctorService = new DoctorService(doctorRepository, facilityRepository, doctorMapper);
    }

    @Test
    void getDoctors_getDoctorList_dtoListReturned() {
        List<Doctor> list = TestData.DoctorFactory.getList();
        Pageable pageable = PageRequest.of(0, 10);
        when(doctorRepository.findAll(pageable)).thenReturn(new PageImpl<>(list));
        List<DoctorDto> expectedResult = TestData.DoctorDtoFactory.getList();

        List<DoctorDto> result = doctorService.getDoctors(pageable);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getDoctor_noDoctorInDb_CorrectException() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(NotFoundException.class,
                () -> doctorService.getDoctor(1L));

        Assertions.assertEquals(exception.getMessage(), "Non-existent doctor");
    }

    @Test
    void getDoctor_doctorFound_DtoReturned() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(TestData.DoctorFactory.get(1L)));
        DoctorDto expectedResult = TestData.DoctorDtoFactory.get(1L);
        expectedResult.setId(1L);

        DoctorDto result = doctorService.getDoctor(1L);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void addDoctor_doctorAdded_DtoReturned() {
        DoctorDto data = TestData.DoctorDtoFactory.get(1L);
        DoctorDto expectedResult = TestData.DoctorDtoFactory.get(1L);

        DoctorDto result = doctorService.addDoctor(data);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void editDoctor_noDoctorInDb_CorrectException() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(NotFoundException.class,
                () -> doctorService.editDoctor(1L, new DoctorDto()));

        Assertions.assertEquals(exception.getMessage(), "Non-existent doctor");
    }

    @Test
    void editDoctor_correctApproach_DtoReturned() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        DoctorDto expectedResult = TestData.DoctorDtoFactory.get(1L);

        DoctorDto result = doctorService.editDoctor(1L, TestData.DoctorDtoFactory.get());

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void deleteDoctor_doctorInDb_doctorDeleted() {
        Doctor doctor = TestData.DoctorFactory.get(1L);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        doctorService.deleteDoctor(1L);

        verify(doctorRepository, times(1)).delete(doctor);
    }

    @Test
    void deleteDoctor_noDoctorInDb_functionReturned() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        doctorService.deleteDoctor(1L);

        verify(doctorRepository, times(0)).delete(new Doctor());
    }

    @Test
    void addDoctorFacility_noDoctorInDb_CorrectException() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(NotFoundException.class,
                () -> doctorService.addDoctorFacility(1L, 1L));

        Assertions.assertEquals(exception.getMessage(), "Non-existent doctor");
    }

    @Test
    void addDoctorFacility_noFacilityInDb_CorrectException() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(new Doctor()));
        when(facilityRepository.findById(1L)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(NotFoundException.class,
                () -> doctorService.addDoctorFacility(1L, 1L));

        Assertions.assertEquals(exception.getMessage(), "Non-existent facility");
    }

    @Test
    void addDoctorFacility_correctApproach_DtoReturned() {
        Doctor doctor = TestData.DoctorFactory.get(1L);
        doctor.getFacilities().add(TestData.FacilityFactory.get(1L));
        doctor.getFacilities().add(TestData.FacilityFactory.get(2L));
        Facility facility = TestData.FacilityFactory.get(3L);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(facilityRepository.findById(3L)).thenReturn(Optional.of(facility));
        Set<Long> expectedIds = new HashSet<>();
        expectedIds.add(1L);
        expectedIds.add(2L);
        expectedIds.add(3L);

        DoctorDto result = doctorService.addDoctorFacility(1L, 3L);

        Set<Long> actualIds = result.getFacilities().stream()
                .map(FacilitySimpleDto::getId)
                .collect(Collectors.toSet());
        Assertions.assertEquals(doctor.getId(), result.getId());
        Assertions.assertEquals(doctor.getFacilities().size(), result.getFacilities().size());
        Assertions.assertTrue(actualIds.containsAll(expectedIds));
    }

    @Test
    void removeDoctorFacility_noDoctorInDb_CorrectException() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(NotFoundException.class,
                () -> doctorService.removeDoctorFacility(1L, 1L));

        Assertions.assertEquals(exception.getMessage(), "Non-existent doctor");
    }

    @Test
    void removeDoctorFacility_noFacilityInDb_CorrectException() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(new Doctor()));
        when(facilityRepository.findById(1L)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(NotFoundException.class,
                () -> doctorService.removeDoctorFacility(1L, 1L));

        Assertions.assertEquals(exception.getMessage(), "Non-existent facility");
    }

    @Test
    void removeDoctorFacility_correctApproach_DtoReturned() {
        Doctor doctor = TestData.DoctorFactory.get(1L);
        doctor.getFacilities().add(TestData.FacilityFactory.get(1L));
        doctor.getFacilities().add(TestData.FacilityFactory.get(2L));
        Facility facility = TestData.FacilityFactory.get(2L);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(facilityRepository.findById(2L)).thenReturn(Optional.of(facility));
        Set<Long> expectedIds = new HashSet<>();
        expectedIds.add(1L);

        DoctorDto result = doctorService.removeDoctorFacility(1L, 2L);

        Set<Long> actualIds = result.getFacilities().stream()
                .map(FacilitySimpleDto::getId)
                .collect(Collectors.toSet());
        Assertions.assertEquals(doctor.getId(), result.getId());
        Assertions.assertEquals(doctor.getFacilities().size(), result.getFacilities().size());
        Assertions.assertTrue(actualIds.containsAll(expectedIds));
    }
}
