package com.LuuQu.medicalclinic.controller;

import com.LuuQu.medicalclinic.testHelper.TestControllerHelper;
import com.LuuQu.medicalclinic.testHelper.TestData;
import com.LuuQu.medicalclinic.model.dto.AppointmentDto;
import com.LuuQu.medicalclinic.service.AppointmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerTest {
    @MockBean
    AppointmentService appointmentService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void addAppointment_DataCorrect_DtoReturned() throws Exception {
        AppointmentDto appointmentDtoInput = TestData.AppointmentDtoFactory.get();
        Long doctorId = 1L;
        AppointmentDto appointmentDtoOutput = TestData.AppointmentDtoFactory.get(1L);
        appointmentDtoOutput.setDoctor(TestData.DoctorSimpleDtoFactory.get(1L));
        when(appointmentService.addAppointment(doctorId, appointmentDtoInput)).thenReturn(appointmentDtoOutput);
        String requestJson = TestControllerHelper.getObjectAsString(appointmentDtoInput, objectMapper);
        String expectedResult = TestControllerHelper.getObjectAsString(appointmentDtoOutput, objectMapper);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/appointments/{doctorId}", doctorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        Assertions.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    void addPatient_DataCorrect_DtoReturned() throws Exception {
        AppointmentDto appointmentDto = TestData.AppointmentDtoFactory.get(1L);
        appointmentDto.setDoctor(TestData.DoctorSimpleDtoFactory.get(1L));
        appointmentDto.setPatient(TestData.PatientDtoFactory.get(1L));
        when(appointmentService.addPatient(1L, 1L)).thenReturn(appointmentDto);
        String expectedResult = TestControllerHelper.getObjectAsString(appointmentDto, objectMapper);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.put("/appointments/{appointmentId}/patients/{patientId}", 1L, 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(removeEmptyLines(expectedResult), result.getResponse().getContentAsString());
    }

    @Test
    void getPatientAppointments_DataCorrect_DtoListReturned() throws Exception {
        List<AppointmentDto> appointmentDtoList = TestData.AppointmentDtoFactory.getList(2);
        for (AppointmentDto appointmentDto : appointmentDtoList) {
            appointmentDto.setPatient(TestData.PatientDtoFactory.get(1L));
        }
        when(appointmentService.getPatientAppointments(1L)).thenReturn(appointmentDtoList);
        String expectedResult = TestControllerHelper.getObjectAsString(appointmentDtoList, objectMapper);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/appointments/patient/{patientId}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(removeEmptyLines(expectedResult), result.getResponse().getContentAsString());
    }

    @Test
    void getDoctorAppointments_DataCorrect_DtoListReturned() throws Exception {
        List<AppointmentDto> appointmentDtoList = TestData.AppointmentDtoFactory.getList(2);
        for (AppointmentDto appointmentDto : appointmentDtoList) {
            appointmentDto.setDoctor(TestData.DoctorSimpleDtoFactory.get(1L));
        }
        when(appointmentService.getDoctorAppointments(1L)).thenReturn(appointmentDtoList);
        String expectedResult = TestControllerHelper.getObjectAsString(appointmentDtoList, objectMapper);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/appointments/doctor/{doctorId}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(removeEmptyLines(expectedResult), result.getResponse().getContentAsString());
    }

    @Test
    void getDoctorAvailableHours_DataCorrect_DtoListReturned() throws Exception {
        LocalDate date = LocalDate.now();
        String specialization = "specialization";
        Long doctorId = 1L;
        List<AppointmentDto> appointmentDtoList = TestData.AppointmentDtoFactory.getList(2);
        for (AppointmentDto appointmentDto : appointmentDtoList) {
            appointmentDto.setDoctor(TestData.DoctorSimpleDtoFactory.get(doctorId));
        }
        when(appointmentService.getDoctorAvailableHours(date, specialization, doctorId)).thenReturn(appointmentDtoList);
        String expectedResult = TestControllerHelper.getObjectAsString(appointmentDtoList, objectMapper);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/appointments/doctor")
                        .param("date", date.toString())
                        .param("specialization", specialization)
                        .param("doctorId", doctorId.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(removeEmptyLines(expectedResult), result.getResponse().getContentAsString());
    }

    String removeEmptyLines(String input) {
        return Arrays.stream(input.split("\r\n"))
                .filter(line -> !line.trim().isEmpty())
                .collect(Collectors.joining(""))
                .replaceAll(" ", "");
    }
}
