package springboot.application.professionaltype.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ProfessionalTypeNotFoundApplicationException extends NotFoundApplicationException {
    public ProfessionalTypeNotFoundApplicationException(String id) {
        super("ProfessionalType", id);
    }
}
