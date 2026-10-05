package springboot.application.treatmentgoalstatus.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class TreatmentGoalStatusNotFoundApplicationException extends NotFoundApplicationException {
    public TreatmentGoalStatusNotFoundApplicationException(String id) {
        super("TreatmentGoalStatus", id);
    }
}
