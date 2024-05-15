package com.LuuQu.medicalclinic.mapper;

import com.LuuQu.medicalclinic.model.dto.PatientDto;
import com.LuuQu.medicalclinic.model.entity.Patient;
import org.modelmapper.ModelMapper;

public class PatientMapper {
    public static ModelMapper modelMapper = new ModelMapper();
    public static Patient mapPatientDtoToEntity(PatientDto patientDto) {
        return modelMapper.map(patientDto, Patient.class);
    }
    public static PatientDto mapPatientEntityToDto(Patient patient) {
        return modelMapper.map(patient,PatientDto.class);
    }
}
