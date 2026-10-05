package springboot.application.sendertype.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class SenderTypeNotFoundApplicationException extends NotFoundApplicationException {
    public SenderTypeNotFoundApplicationException(String id) {
        super("SenderType", id);
    }
}
