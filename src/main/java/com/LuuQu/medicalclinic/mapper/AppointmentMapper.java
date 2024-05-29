package com.LuuQu.medicalclinic.mapper;

import com.LuuQu.medicalclinic.model.dto.AppointmentDto;
import com.LuuQu.medicalclinic.model.entity.Appointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DoctorMapper.class, PatientMapper.class})
public interface AppointmentMapper {
    AppointmentDto toDto(Appointment appointment);

    Appointment toEntity(AppointmentDto appointmentDto);
}
