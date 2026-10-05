package springboot.application.emailcontact.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class EmailContactNotFoundApplicationException extends NotFoundApplicationException {
    public EmailContactNotFoundApplicationException(String id) {
        super("EmailContact", id);
    }
}
