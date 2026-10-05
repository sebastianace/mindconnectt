package springboot.application.treatmentgoal.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class TreatmentGoalNotFoundApplicationException extends NotFoundApplicationException {
    public TreatmentGoalNotFoundApplicationException(String id) {
        super("TreatmentGoal", id);
    }
}
