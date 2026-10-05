package springboot.application.patientcontact.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class PatientContactNotFoundApplicationException extends NotFoundApplicationException {
    public PatientContactNotFoundApplicationException(String id) {
        super("PatientContact", id);
    }
}
