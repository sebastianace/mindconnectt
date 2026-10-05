package springboot.application.medicationroute.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class MedicationRouteNotFoundApplicationException extends NotFoundApplicationException {
    public MedicationRouteNotFoundApplicationException(String id) {
        super("MedicationRoute", id);
    }
}
