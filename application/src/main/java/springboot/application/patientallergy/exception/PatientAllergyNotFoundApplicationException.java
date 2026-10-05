package springboot.application.patientallergy.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class PatientAllergyNotFoundApplicationException extends NotFoundApplicationException {
    public PatientAllergyNotFoundApplicationException(String id) {
        super("PatientAllergy", id);
    }
}
