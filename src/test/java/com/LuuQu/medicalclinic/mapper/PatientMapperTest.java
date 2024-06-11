package com.LuuQu.medicalclinic.mapper;

import com.LuuQu.medicalclinic.model.dto.PatientDto;
import com.LuuQu.medicalclinic.model.entity.Patient;
import com.LuuQu.medicalclinic.testHelper.TestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class PatientMapperTest {
    private PatientMapper patientMapper;

    @BeforeEach
    void setUp() {
        patientMapper = new PatientMapperImpl();
    }

    @ParameterizedTest
    @MethodSource
    void toDtoTest(Patient patientToTest, PatientDto expectedResult) {
        PatientDto result = patientMapper.toDto(patientToTest);

        Assertions.assertEquals(expectedResult, result);
    }

    private static Stream<Arguments> toDtoTest() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(new Patient(), new PatientDto()),
                Arguments.of(TestData.PatientFactory.get(1L), TestData.PatientDtoFactory.get(1L))
        );
    }

    @ParameterizedTest
    @MethodSource
    void toEntityTest(PatientDto patientDtoToTest, Patient expectedResult) {
        Patient result = patientMapper.toEntity(patientDtoToTest);

        if (expectedResult == null) {
            Assertions.assertNull(result);
            return;
        }
        Assertions.assertEquals(expectedResult.getId(), result.getId());
        Assertions.assertEquals(expectedResult.getIdCardNo(), result.getIdCardNo());
        Assertions.assertEquals(expectedResult.getPhoneNumber(), result.getPhoneNumber());
        Assertions.assertEquals(expectedResult.getBirthday(), result.getBirthday());
        Assertions.assertEquals(expectedResult.getUser().getFirstName(), result.getUser().getFirstName());
        Assertions.assertEquals(expectedResult.getUser().getLastName(), result.getUser().getLastName());
        Assertions.assertEquals(expectedResult.getUser().getEmail(), result.getUser().getEmail());
        Assertions.assertEquals(expectedResult.getUser().getPassword(), result.getUser().getPassword());
    }

    private static Stream<Arguments> toEntityTest() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(new PatientDto(), new Patient()),
                Arguments.of(TestData.PatientDtoFactory.get(1L), TestData.PatientFactory.get(1L))
        );
    }
}
