package com.LuuQu.medicalclinic.exception;

public class AppointmentException extends MedicalClinicException {
    public AppointmentException() {
        super();
    }
    public AppointmentException(String message) {
        super(message);
    }
    public AppointmentException(String message, Throwable cause) {
        super(message, cause);
    }
    public AppointmentException(Throwable cause) {
        super(cause);
    }
}
