package com.LuuQu.medicalclinic.controller;

import com.LuuQu.medicalclinic.testHelper.TestControllerHelper;
import com.LuuQu.medicalclinic.testHelper.TestData;
import com.LuuQu.medicalclinic.model.dto.DoctorDto;
import com.LuuQu.medicalclinic.service.DoctorService;
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
public class DoctorControllerTest {
    @MockBean
    DoctorService doctorService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getDoctorList_CorrectData_DtoListReturned() throws Exception {
        List<DoctorDto> list = TestData.DoctorDtoFactory.getList(10);
        String expectedResult = TestControllerHelper.getObjectAsString(list, objectMapper);
        when(doctorService.getDoctors(PageRequest.of(0, 10))).thenReturn(list);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/doctors")
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    void getDoctor_CorrectData_DtoListReturned() throws Exception {
        DoctorDto doctorDto = TestData.DoctorDtoFactory.get(1L);
        String expectedResult = TestControllerHelper.getObjectAsString(doctorDto, objectMapper);
        when(doctorService.getDoctor(1L)).thenReturn(doctorDto);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/doctors/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    void addDoctor_CorrectData_DtoReturned() throws Exception {
        DoctorDto doctorDto = TestData.DoctorDtoFactory.get(1L);
        String request = TestControllerHelper.getObjectAsString(TestData.DoctorDtoFactory.get(), objectMapper);
        String expectedResult = TestControllerHelper.getObjectAsString(doctorDto, objectMapper);
        when(doctorService.addDoctor(TestData.DoctorDtoFactory.get())).thenReturn(doctorDto);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        Assertions.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    void updateDoctor_CorrectData_DtoReturned() throws Exception {
        DoctorDto doctorDto = TestData.DoctorDtoFactory.get(1L);
        String request = TestControllerHelper.getObjectAsString(TestData.DoctorDtoFactory.get(), objectMapper);
        String expectedResult = TestControllerHelper.getObjectAsString(doctorDto, objectMapper);
        when(doctorService.editDoctor(1L, TestData.DoctorDtoFactory.get())).thenReturn(doctorDto);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.patch("/doctors/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    void deleteDoctor_CorrectData_DtoReturned() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/doctors/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(doctorService, times(1)).deleteDoctor(1L);
    }

    @Test
    void addDoctorFacility_CorrectData_DtoReturned() throws Exception {
        DoctorDto doctorDto = TestData.DoctorDtoFactory.get(1L);
        doctorDto.getFacilities().add(TestData.FacilitySimpleDtoFactory.get(1L));
        String expectedResult = TestControllerHelper.getObjectAsString(doctorDto, objectMapper);
        when(doctorService.addDoctorFacility(1L, 1L)).thenReturn(doctorDto);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/doctors/{doctorId}/facilities/{facilityId}", 1L, 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    void removeDoctorFacility_CorrectData_DtoReturned() throws Exception {
        DoctorDto doctorDto = TestData.DoctorDtoFactory.get(1L);
        String expectedResult = TestControllerHelper.getObjectAsString(doctorDto, objectMapper);
        when(doctorService.removeDoctorFacility(1L, 1L)).thenReturn(doctorDto);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/doctors/{doctorId}/facilities/{facilityId}", 1L, 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }
}
