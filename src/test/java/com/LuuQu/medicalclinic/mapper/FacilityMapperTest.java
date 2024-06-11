package com.LuuQu.medicalclinic.mapper;

import com.LuuQu.medicalclinic.model.dto.FacilityDto;
import com.LuuQu.medicalclinic.model.entity.Facility;
import com.LuuQu.medicalclinic.testHelper.TestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.LinkedHashSet;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

public class FacilityMapperTest {
    private FacilityMapper facilityMapper;
    private DoctorMapper doctorMapper;

    @BeforeEach
    void setUp() {
        facilityMapper = new FacilityMapperImpl();
        doctorMapper = Mockito.mock(DoctorMapperImpl.class);
        ReflectionTestUtils.setField(facilityMapper, "doctorMapper", doctorMapper);
    }

    @ParameterizedTest
    @MethodSource
    void toDtoTest(Facility facilityToTest, FacilityDto expectedResult) {
        when(doctorMapper.toSimpleDto(TestData.DoctorFactory.get(1L))).thenReturn(TestData.DoctorSimpleDtoFactory.get(1L));
        when(doctorMapper.toSimpleDto(TestData.DoctorFactory.get(2L))).thenReturn(TestData.DoctorSimpleDtoFactory.get(2L));

        FacilityDto result = facilityMapper.toDto(facilityToTest);

        if (expectedResult == null) {
            Assertions.assertNull(result);
            return;
        }
        Assertions.assertEquals(expectedResult.getId(), result.getId());
        Assertions.assertEquals(expectedResult.getName(), result.getName());
        Assertions.assertEquals(expectedResult.getCity(), result.getCity());
        Assertions.assertEquals(expectedResult.getZipCode(), result.getZipCode());
        Assertions.assertEquals(expectedResult.getStreet(), result.getStreet());
        Assertions.assertEquals(expectedResult.getBuildingNo(), result.getBuildingNo());
        Assertions.assertEquals(expectedResult.getDoctors(), result.getDoctors());
    }

    private static Stream<Arguments> toDtoTest() {
        Facility fullFacility = TestData.FacilityFactory.get(1L);
        fullFacility.setDoctors(new LinkedHashSet<>());
        fullFacility.getDoctors().add(TestData.DoctorFactory.get(1L));
        fullFacility.getDoctors().add(TestData.DoctorFactory.get(2L));
        FacilityDto fullExpectedResult = TestData.FacilityDtoFactory.get(1L);
        fullExpectedResult.setDoctors(new LinkedHashSet<>());
        fullExpectedResult.getDoctors().add(TestData.DoctorSimpleDtoFactory.get(1L));
        fullExpectedResult.getDoctors().add(TestData.DoctorSimpleDtoFactory.get(2L));

        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(new Facility(), new FacilityDto()),
                Arguments.of(fullFacility, fullExpectedResult)
        );
    }

    @ParameterizedTest
    @MethodSource
    void toEntityTest(FacilityDto facilityToTest, Facility expectedResult) {
        when(doctorMapper.toEntity(TestData.DoctorSimpleDtoFactory.get(1L))).thenReturn(TestData.DoctorFactory.get(1L));
        when(doctorMapper.toEntity(TestData.DoctorSimpleDtoFactory.get(2L))).thenReturn(TestData.DoctorFactory.get(2L));

        Facility result = facilityMapper.toEntity(facilityToTest);

        if (expectedResult == null) {
            Assertions.assertNull(result);
            return;
        }
        Assertions.assertEquals(expectedResult.getId(), result.getId());
        Assertions.assertEquals(expectedResult.getName(), result.getName());
        Assertions.assertEquals(expectedResult.getCity(), result.getCity());
        Assertions.assertEquals(expectedResult.getZipCode(), result.getZipCode());
        Assertions.assertEquals(expectedResult.getStreet(), result.getStreet());
        Assertions.assertEquals(expectedResult.getBuildingNo(), result.getBuildingNo());
        Assertions.assertEquals(expectedResult.getDoctors(), result.getDoctors());
    }

    private static Stream<Arguments> toEntityTest() {
        FacilityDto fullFacilityDto = TestData.FacilityDtoFactory.get(1L);
        fullFacilityDto.setDoctors(new LinkedHashSet<>());
        fullFacilityDto.getDoctors().add(TestData.DoctorSimpleDtoFactory.get(1L));
        fullFacilityDto.getDoctors().add(TestData.DoctorSimpleDtoFactory.get(2L));
        Facility fullExpectedResult = TestData.FacilityFactory.get(1L);
        fullExpectedResult.setDoctors(new LinkedHashSet<>());
        fullExpectedResult.getDoctors().add(TestData.DoctorFactory.get(1L));
        fullExpectedResult.getDoctors().add(TestData.DoctorFactory.get(2L));

        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(new FacilityDto(), new Facility()),
                Arguments.of(fullFacilityDto, fullExpectedResult)
        );
    }
}
