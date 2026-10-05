package springboot.application.contact.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ContactNotFoundApplicationException extends NotFoundApplicationException {
    public ContactNotFoundApplicationException(String id) {
        super("Contact", id);
    }
}
