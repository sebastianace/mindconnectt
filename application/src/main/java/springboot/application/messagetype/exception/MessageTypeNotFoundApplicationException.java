package springboot.application.messagetype.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class MessageTypeNotFoundApplicationException extends NotFoundApplicationException {
    public MessageTypeNotFoundApplicationException(String id) {
        super("MessageType", id);
    }
}
