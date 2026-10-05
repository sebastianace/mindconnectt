package springboot.application.phonecontact.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class PhoneContactNotFoundApplicationException extends NotFoundApplicationException {
    public PhoneContactNotFoundApplicationException(String id) {
        super("PhoneContact", id);
    }
}
