package com.LuuQu.medicalclinic.mapper;

import com.LuuQu.medicalclinic.model.dto.DoctorDto;
import com.LuuQu.medicalclinic.model.dto.DoctorSimpleDto;
import com.LuuQu.medicalclinic.model.dto.PatientSimpleDto;
import com.LuuQu.medicalclinic.model.entity.Appointment;
import com.LuuQu.medicalclinic.model.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    PatientMapper patientMapper = Mappers.getMapper(PatientMapper.class);
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "password", source = "user.password")
    @Mapping(target = "patients", source = ".", qualifiedByName = "getPatients")
    DoctorDto toDto(Doctor doctor);

    @Mapping(target = "user.firstName", source = "firstName")
    @Mapping(target = "user.lastName", source = "lastName")
    @Mapping(target = "user.email", source = "email")
    @Mapping(target = "user.password", source = "password")
    Doctor toEntity(DoctorDto doctorDto);

    @Mapping(target = "user.firstName", source = "firstName")
    @Mapping(target = "user.lastName", source = "lastName")
    @Mapping(target = "user.email", source = "email")
    @Mapping(target = "user.password", source = "password")
    Doctor toEntity(DoctorSimpleDto doctorSimpleDto);

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "password", source = "user.password")
    DoctorSimpleDto toSimpleDto(Doctor doctor);

    @Named("getPatients")
    default Set<PatientSimpleDto> getPatients(Doctor doctor) {
        return doctor.getAppointments().stream()
                .map(Appointment::getPatient)
                .map(patientMapper::toSimpleDto)
                .collect(Collectors.toSet());
    }
}
