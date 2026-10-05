package springboot.application.conversationstatus.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ConversationStatusNotFoundApplicationException extends NotFoundApplicationException {
    public ConversationStatusNotFoundApplicationException(String id) {
        super("ConversationStatus", id);
    }
}
