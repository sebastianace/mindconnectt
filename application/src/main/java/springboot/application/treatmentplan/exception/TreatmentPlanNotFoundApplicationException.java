package springboot.application.treatmentplan.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class TreatmentPlanNotFoundApplicationException extends NotFoundApplicationException {
    public TreatmentPlanNotFoundApplicationException(String id) {
        super("TreatmentPlan", id);
    }
}
