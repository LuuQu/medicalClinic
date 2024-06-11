package com.LuuQu.medicalclinic.mapper;

import com.LuuQu.medicalclinic.model.dto.DoctorDto;
import com.LuuQu.medicalclinic.model.dto.DoctorSimpleDto;
import com.LuuQu.medicalclinic.model.entity.Doctor;
import com.LuuQu.medicalclinic.testHelper.TestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedHashSet;
import java.util.stream.Stream;

public class DoctorMapperTest {
    DoctorMapper doctorMapper;

    @BeforeEach
    void setUp() {
        doctorMapper = new DoctorMapperImpl();
    }

    @ParameterizedTest
    @MethodSource
    void toDtoTest(Doctor doctorToTest, DoctorDto expectedResult) {
        DoctorDto result = doctorMapper.toDto(doctorToTest);

        if (expectedResult == null) {
            Assertions.assertNull(result);
            return;
        }
        Assertions.assertEquals(expectedResult.getId(), result.getId());
        Assertions.assertEquals(expectedResult.getEmail(), result.getEmail());
        Assertions.assertEquals(expectedResult.getPassword(), result.getPassword());
        Assertions.assertEquals(expectedResult.getFirstName(), result.getFirstName());
        Assertions.assertEquals(expectedResult.getLastName(), result.getLastName());
        Assertions.assertEquals(expectedResult.getSpecialization(), result.getSpecialization());
        Assertions.assertEquals(expectedResult.getFacilities(), result.getFacilities());
    }

    private static Stream<Arguments> toDtoTest() {
        Doctor fullDoctor = TestData.DoctorFactory.get(1L);
        fullDoctor.setFacilities(new LinkedHashSet<>());
        fullDoctor.getFacilities().add(TestData.FacilityFactory.get(1L));
        fullDoctor.getFacilities().add(TestData.FacilityFactory.get(2L));
        DoctorDto fullExpectedResult = TestData.DoctorDtoFactory.get(1L);
        fullExpectedResult.setFacilities(new LinkedHashSet<>());
        fullExpectedResult.getFacilities().add(TestData.FacilitySimpleDtoFactory.get(1L));
        fullExpectedResult.getFacilities().add(TestData.FacilitySimpleDtoFactory.get(2L));

        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(new Doctor(), new DoctorDto()),
                Arguments.of(fullDoctor, fullExpectedResult)
        );
    }

    @ParameterizedTest
    @MethodSource
    void toEntityTestDto(DoctorDto doctorDtoToTest, Doctor expectedResult) {
        Doctor result = doctorMapper.toEntity(doctorDtoToTest);

        if (expectedResult == null) {
            Assertions.assertNull(result);
            return;
        }
        Assertions.assertEquals(expectedResult.getId(), result.getId());
        Assertions.assertEquals(expectedResult.getUser().getEmail(), result.getUser().getEmail());
        Assertions.assertEquals(expectedResult.getUser().getPassword(), result.getUser().getPassword());
        Assertions.assertEquals(expectedResult.getUser().getFirstName(), result.getUser().getFirstName());
        Assertions.assertEquals(expectedResult.getUser().getLastName(), result.getUser().getLastName());
        Assertions.assertEquals(expectedResult.getSpecialization(), result.getSpecialization());
        Assertions.assertEquals(expectedResult.getFacilities(), result.getFacilities());
    }

    private static Stream<Arguments> toEntityTestDto() {
        DoctorDto fullDoctorDto = TestData.DoctorDtoFactory.get(1L);
        fullDoctorDto.setFacilities(new LinkedHashSet<>());
        fullDoctorDto.getFacilities().add(TestData.FacilitySimpleDtoFactory.get(1L));
        fullDoctorDto.getFacilities().add(TestData.FacilitySimpleDtoFactory.get(2L));
        Doctor fullExpectedResult = TestData.DoctorFactory.get(1L);
        fullExpectedResult.setFacilities(new LinkedHashSet<>());
        fullExpectedResult.getFacilities().add(TestData.FacilityFactory.get(1L));
        fullExpectedResult.getFacilities().add(TestData.FacilityFactory.get(2L));

        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(new DoctorDto(), new Doctor()),
                Arguments.of(fullDoctorDto, fullExpectedResult)
        );
    }

    @ParameterizedTest
    @MethodSource
    void toEntityTestSimpleDto(DoctorSimpleDto doctorSimpleDtoToTest, Doctor expectedResult) {
        Doctor result = doctorMapper.toEntity(doctorSimpleDtoToTest);

        if (expectedResult == null) {
            Assertions.assertNull(result);
            return;
        }
        Assertions.assertEquals(expectedResult.getId(), result.getId());
        Assertions.assertEquals(expectedResult.getUser().getEmail(), result.getUser().getEmail());
        Assertions.assertEquals(expectedResult.getUser().getPassword(), result.getUser().getPassword());
        Assertions.assertEquals(expectedResult.getUser().getFirstName(), result.getUser().getFirstName());
        Assertions.assertEquals(expectedResult.getUser().getLastName(), result.getUser().getLastName());
        Assertions.assertEquals(expectedResult.getSpecialization(), result.getSpecialization());
        Assertions.assertEquals(expectedResult.getFacilities(), result.getFacilities());
    }

    private static Stream<Arguments> toEntityTestSimpleDto() {
        DoctorSimpleDto fullDoctorSimpleDto = TestData.DoctorSimpleDtoFactory.get(1L);
        Doctor fullExpectedResult = TestData.DoctorFactory.get(1L);

        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(new DoctorSimpleDto(), new Doctor()),
                Arguments.of(fullDoctorSimpleDto, fullExpectedResult)
        );
    }

    @ParameterizedTest
    @MethodSource
    void toSimpleDtoTest(Doctor doctorToTest, DoctorSimpleDto expectedResult) {
        DoctorSimpleDto result = doctorMapper.toSimpleDto(doctorToTest);

        if (expectedResult == null) {
            Assertions.assertNull(result);
            return;
        }

        Assertions.assertEquals(expectedResult.getId(), result.getId());
        Assertions.assertEquals(expectedResult.getEmail(), result.getEmail());
        Assertions.assertEquals(expectedResult.getPassword(), result.getPassword());
        Assertions.assertEquals(expectedResult.getFirstName(), result.getFirstName());
        Assertions.assertEquals(expectedResult.getLastName(), result.getLastName());
        Assertions.assertEquals(expectedResult.getSpecialization(), result.getSpecialization());
    }

    private static Stream<Arguments> toSimpleDtoTest() {
        Doctor fullDoctor = TestData.DoctorFactory.get(1L);
        fullDoctor.getFacilities().add(TestData.FacilityFactory.get(1L));
        fullDoctor.getFacilities().add(TestData.FacilityFactory.get(2L));
        fullDoctor.getAppointments().add(TestData.AppointmentFactory.get(1L));
        fullDoctor.getAppointments().add(TestData.AppointmentFactory.get(2L));
        DoctorSimpleDto fullExpectedResult = TestData.DoctorSimpleDtoFactory.get(1L);

        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(new Doctor(), new DoctorSimpleDto()),
                Arguments.of(fullDoctor, fullExpectedResult)
        );
    }
}
