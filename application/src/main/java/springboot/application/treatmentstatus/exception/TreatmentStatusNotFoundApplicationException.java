package springboot.application.treatmentstatus.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class TreatmentStatusNotFoundApplicationException extends NotFoundApplicationException {
    public TreatmentStatusNotFoundApplicationException(String id) {
        super("TreatmentStatus", id);
    }
}
