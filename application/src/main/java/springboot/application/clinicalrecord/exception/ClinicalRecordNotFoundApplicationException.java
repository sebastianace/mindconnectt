package springboot.application.clinicalrecord.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ClinicalRecordNotFoundApplicationException extends NotFoundApplicationException {
    public ClinicalRecordNotFoundApplicationException(String id) {
        super("ClinicalRecord", id);
    }
}
