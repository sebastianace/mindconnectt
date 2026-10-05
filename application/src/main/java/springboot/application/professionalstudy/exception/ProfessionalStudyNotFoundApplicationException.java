package springboot.application.professionalstudy.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ProfessionalStudyNotFoundApplicationException extends NotFoundApplicationException {
    public ProfessionalStudyNotFoundApplicationException(String id) {
        super("ProfessionalStudy", id);
    }
}
