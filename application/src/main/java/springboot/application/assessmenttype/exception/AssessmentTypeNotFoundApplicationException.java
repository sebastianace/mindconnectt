package springboot.application.assessmenttype.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class AssessmentTypeNotFoundApplicationException extends NotFoundApplicationException {
    public AssessmentTypeNotFoundApplicationException(String id) {
        super("AssessmentType", id);
    }
}
