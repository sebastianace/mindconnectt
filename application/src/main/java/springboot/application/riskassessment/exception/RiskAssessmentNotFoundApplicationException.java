package springboot.application.riskassessment.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class RiskAssessmentNotFoundApplicationException extends NotFoundApplicationException {
    public RiskAssessmentNotFoundApplicationException(String id) {
        super("RiskAssessment", id);
    }
}
