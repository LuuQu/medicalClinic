package com.LuuQu.medicalclinic.mapper;

import com.LuuQu.medicalclinic.model.dto.PatientDto;
import com.LuuQu.medicalclinic.model.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDto mapPatientEntityToDto(Patient patient);

    Patient mapPatientDtoToEntity(PatientDto patientDto);
}
