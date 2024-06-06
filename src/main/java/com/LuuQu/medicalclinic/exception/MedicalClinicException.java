package com.LuuQu.medicalclinic.exception;

public class MedicalClinicException extends RuntimeException {
    public MedicalClinicException() {
        super();
    }
    public MedicalClinicException(String message) {
        super(message);
    }
    public MedicalClinicException(String message, Throwable cause) {
        super(message, cause);
    }
    public MedicalClinicException(Throwable cause) {
        super(cause);
    }
}
