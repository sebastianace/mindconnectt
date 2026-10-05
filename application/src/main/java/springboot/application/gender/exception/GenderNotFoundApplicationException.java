package springboot.application.gender.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class GenderNotFoundApplicationException extends NotFoundApplicationException {
    public GenderNotFoundApplicationException(String id) {
        super("Gender", id);
    }
}
