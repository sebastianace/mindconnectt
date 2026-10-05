package springboot.application.patient.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class PatientNotFoundApplicationException extends NotFoundApplicationException {
    public PatientNotFoundApplicationException(String id) {
        super("Patient", id);
    }
}
