package springboot.application.consenttype.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ConsentTypeNotFoundApplicationException extends NotFoundApplicationException {
    public ConsentTypeNotFoundApplicationException(String id) {
        super("ConsentType", id);
    }
}
