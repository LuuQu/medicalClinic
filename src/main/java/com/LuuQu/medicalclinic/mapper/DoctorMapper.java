package com.LuuQu.medicalclinic.mapper;

import com.LuuQu.medicalclinic.model.dto.DoctorDto;
import com.LuuQu.medicalclinic.model.dto.DoctorSimpleDto;
import com.LuuQu.medicalclinic.model.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "password", source = "user.password")
    DoctorDto toDto(Doctor doctor);

    @Mapping(target = "user.firstName", source = "firstName")
    @Mapping(target = "user.lastName", source = "lastName")
    @Mapping(target = "user.email", source = "email")
    @Mapping(target = "user.password", source = "password")
    Doctor toEntity(DoctorDto doctorDto);

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "password", source = "user.password")
    DoctorSimpleDto toSimpleDto(Doctor doctor);
}
