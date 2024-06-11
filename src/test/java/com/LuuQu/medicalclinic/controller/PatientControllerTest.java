package com.LuuQu.medicalclinic.controller;

import com.LuuQu.medicalclinic.model.dto.PatientDto;
import com.LuuQu.medicalclinic.service.PatientService;
import com.LuuQu.medicalclinic.testHelper.TestControllerHelper;
import com.LuuQu.medicalclinic.testHelper.TestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {
    @MockBean
    PatientService patientService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getPatientList_CorrectData_DtoListReturned() throws Exception {
        List<PatientDto> list = TestData.PatientDtoFactory.getList(10);
        String expectedResult = TestControllerHelper.getObjectAsString(list, objectMapper);
        when(patientService.getPatients(PageRequest.of(0, 10))).thenReturn(list);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/patients")
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    void getPatient_CorrectData_DtoReturned() throws Exception {
        PatientDto patientDto = TestData.PatientDtoFactory.get(1L);
        String expectedResult = TestControllerHelper.getObjectAsString(patientDto, objectMapper);
        when(patientService.getPatient(1L)).thenReturn(patientDto);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/patients/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    void addPatient_CorrectData_DtoReturned() throws Exception {
        PatientDto patientDto = TestData.PatientDtoFactory.get(1L);
        String request = TestControllerHelper.getObjectAsString(TestData.PatientDtoFactory.get(), objectMapper);
        String expectedResult = TestControllerHelper.getObjectAsString(patientDto, objectMapper);
        when(patientService.addPatient(TestData.PatientDtoFactory.get())).thenReturn(patientDto);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        Assertions.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    void deletePatient_CorrectData_DtoReturned() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/patients/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(patientService, times(1)).deletePatient(1L);
    }

    @Test
    void editPatient_CorrectData_DtoReturned() throws Exception {
        PatientDto patientDto = TestData.PatientDtoFactory.get(1L);
        String request = TestControllerHelper.getObjectAsString(TestData.PatientDtoFactory.get(), objectMapper);
        String expectedResult = TestControllerHelper.getObjectAsString(patientDto, objectMapper);
        when(patientService.editPatient(1L, TestData.PatientDtoFactory.get())).thenReturn(patientDto);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.put("/patients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    void editPassword_CorrectData_DtoReturned() throws Exception {
        PatientDto patientDto = TestData.PatientDtoFactory.get(1L);
        patientDto.setPassword("password2");
        PatientDto requestModel = new PatientDto();
        requestModel.setPassword("password2");
        String request = TestControllerHelper.getObjectAsString(requestModel, objectMapper);
        String expectedResult = TestControllerHelper.getObjectAsString(patientDto, objectMapper);
        when(patientService.editPassword(1L, requestModel)).thenReturn(patientDto);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.patch("/patients/{id}/password", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }
}
