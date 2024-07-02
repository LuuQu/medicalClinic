package com.LuuQu.medicalclinic.handler;

import com.LuuQu.medicalclinic.exception.*;
import com.LuuQu.medicalclinic.model.dto.ErrorMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MedicalClinicException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ErrorMessageDto> medicalClinicExceptionErrorResponse(MedicalClinicException exception) {
        log.error("MedicalClinicException thrown with message = {} and stack trace = {}", exception.getMessage(), exception.getStackTrace());
        return ResponseEntity.badRequest().body(new ErrorMessageDto(exception.getMessage()));
    }

    @ExceptionHandler(AppointmentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ErrorMessageDto> appointmentExceptionErrorResponse(AppointmentException patientException) {
        log.error("AppointmentException thrown with message = {} and stack trace = {}", patientException.getMessage(), patientException.getStackTrace());
        return ResponseEntity.badRequest().body(new ErrorMessageDto(patientException.getMessage()));
    }

    @ExceptionHandler(PatientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ErrorMessageDto> patientExceptionErrorResponse(PatientException patientException) {
        log.error("PatientException thrown with message = {} and stack trace = {}", patientException.getMessage(), patientException.getStackTrace());
        return ResponseEntity.badRequest().body(new ErrorMessageDto(patientException.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ErrorMessageDto> notFoundExceptionErrorResponse(NotFoundException exception) {
        log.error("NotFoundException thrown with message = {} and stack trace = {}", exception.getMessage(), exception.getStackTrace());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageDto(exception.getMessage()));
    }
}
