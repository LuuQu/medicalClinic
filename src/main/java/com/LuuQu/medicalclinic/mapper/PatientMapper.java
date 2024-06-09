package com.LuuQu.medicalclinic.mapper;

import com.LuuQu.medicalclinic.model.dto.DoctorSimpleDto;
import com.LuuQu.medicalclinic.model.dto.PatientDto;
import com.LuuQu.medicalclinic.model.dto.PatientSimpleDto;
import com.LuuQu.medicalclinic.model.entity.Appointment;
import com.LuuQu.medicalclinic.model.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = DoctorMapper.class)
public interface PatientMapper {
    @Autowired
    DoctorMapper doctorMapper = Mappers.getMapper(DoctorMapper.class);

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "password", source = "user.password")
    @Mapping(target = "doctors", source = ".", qualifiedByName = "getDoctors")
    PatientDto toDto(Patient patient);

    @Mapping(target = "user.firstName", source = "firstName")
    @Mapping(target = "user.lastName", source = "lastName")
    @Mapping(target = "user.email", source = "email")
    @Mapping(target = "user.password", source = "password")
    Patient toEntity(PatientDto patientDto);

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "password", source = "user.password")
    PatientSimpleDto toSimpleDto(Patient patient);

    @Named("getDoctors")
    default Set<DoctorSimpleDto> getDoctors(Patient patient) {
        return patient.getAppointments().stream()
                .map(Appointment::getDoctor)
                .map(doctorMapper::toSimpleDto)
                .collect(Collectors.toSet());
    }
}
