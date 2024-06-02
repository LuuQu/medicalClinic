package com.LuuQu.medicalclinic.service;

import com.LuuQu.medicalclinic.factory.TestData;
import com.LuuQu.medicalclinic.mapper.DoctorMapper;
import com.LuuQu.medicalclinic.mapper.FacilityMapper;
import com.LuuQu.medicalclinic.model.dto.FacilityDto;
import com.LuuQu.medicalclinic.model.entity.Facility;
import com.LuuQu.medicalclinic.repository.FacilityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class FacilityServiceTest {
    private FacilityService facilityService;
    private FacilityRepository facilityRepository;

    @BeforeEach
    void setUp() {
        this.facilityRepository = Mockito.mock(FacilityRepository.class);
        FacilityMapper facilityMapper = Mappers.getMapper(FacilityMapper.class);
        DoctorMapper doctorMapper = Mappers.getMapper(DoctorMapper.class);
        ReflectionTestUtils.setField(facilityMapper, "doctorMapper", doctorMapper);
        this.facilityService = new FacilityService(facilityRepository, facilityMapper);
    }

    @Test
    void getFacilities_getFacilityList_dtoListReturned() {
        List<Facility> list = TestData.FacilityFactory.getList();
        Pageable pageable = PageRequest.of(0, 10);
        when(facilityRepository.findAll(pageable)).thenReturn(new PageImpl<>(list));
        List<FacilityDto> expectedResult = TestData.FacilityDtoFactory.getList();

        List<FacilityDto> result = facilityService.getFacilities(pageable);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getFacility_noFacilityInDb_CorrectException() {
        when(facilityRepository.findById(1L)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> facilityService.getFacility(1L));

        Assertions.assertEquals(exception.getMessage(), "Non-existent facility");
    }

    @Test
    void getFacility_correctApproach_DtoReturned() {
        when(facilityRepository.findById(1L)).thenReturn(Optional.of(TestData.FacilityFactory.get()));
        FacilityDto expectedResult = TestData.FacilityDtoFactory.get(1L);

        FacilityDto result = facilityService.getFacility(1L);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void addFacility_correctApproach_DtoReturned() {
        FacilityDto result = facilityService.addFacility(TestData.FacilityDtoFactory.get(1L));

        Assertions.assertEquals(TestData.FacilityDtoFactory.get(1L), result);
    }

    @Test
    void editFacility_noFacilityInDb_CorrectException() {
        when(facilityRepository.findById(1L)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> facilityService.editFacility(1L, new FacilityDto()));

        Assertions.assertEquals(exception.getMessage(), "Non-existent facility");
    }

    @Test
    void editFacility_correctApproach_DtoReturned() {
        FacilityDto facilityDto = TestData.FacilityDtoFactory.get(null);
        Facility facility = new Facility();
        facility.setId(1L);
        when(facilityRepository.findById(1L)).thenReturn(Optional.of(facility));

        FacilityDto result = facilityService.editFacility(1L, facilityDto);

        Assertions.assertEquals(TestData.FacilityDtoFactory.get(1L), result);
    }

    @Test
    void deleteFacility_facilityInDb_facilityDeleted() {
        Facility facility = TestData.FacilityFactory.get(1L);
        when(facilityRepository.findById(1L)).thenReturn(Optional.of(facility));

        facilityService.deleteFacility(1L);

        verify(facilityRepository, times(1)).delete(facility);
    }

    @Test
    void deleteFacility_noFacilityInDb_functionReturned() {
        when(facilityRepository.findById(1L)).thenReturn(Optional.empty());

        facilityService.deleteFacility(1L);

        verify(facilityRepository, times(0)).delete(new Facility());
    }
}
