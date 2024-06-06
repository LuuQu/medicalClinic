package com.LuuQu.medicalclinic.exception;

public class PatientException extends MedicalClinicException {
    public PatientException() {
        super();
    }
    public PatientException(String message) {
        super(message);
    }
    public PatientException(String message, Throwable cause) {
        super(message, cause);
    }
    public PatientException(Throwable cause) {
        super(cause);
    }
}
