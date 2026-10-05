package springboot.application.professional.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ProfessionalNotFoundApplicationException extends NotFoundApplicationException {
    public ProfessionalNotFoundApplicationException(String id) {
        super("Professional", id);
    }
}
