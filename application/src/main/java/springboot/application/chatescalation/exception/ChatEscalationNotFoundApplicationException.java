package springboot.application.chatescalation.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ChatEscalationNotFoundApplicationException extends NotFoundApplicationException {
    public ChatEscalationNotFoundApplicationException(String id) {
        super("ChatEscalation", id);
    }
}
