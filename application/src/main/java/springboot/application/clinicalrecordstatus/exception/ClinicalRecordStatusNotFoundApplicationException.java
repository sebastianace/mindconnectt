package springboot.application.clinicalrecordstatus.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ClinicalRecordStatusNotFoundApplicationException extends NotFoundApplicationException {
    public ClinicalRecordStatusNotFoundApplicationException(String id) {
        super("ClinicalRecordStatus", id);
    }
}
