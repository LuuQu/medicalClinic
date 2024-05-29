package com.LuuQu.medicalclinic.mapper;

import com.LuuQu.medicalclinic.model.dto.PatientDto;
import com.LuuQu.medicalclinic.model.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "password", source = "user.password")
    PatientDto toDto(Patient patient);

    @Mapping(target = "user.firstName", source = "firstName")
    @Mapping(target = "user.lastName", source = "lastName")
    @Mapping(target = "user.email", source = "email")
    @Mapping(target = "user.password", source = "password")
    Patient toEntity(PatientDto patientDto);
}
