package springboot.application.chatairunerror.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ChatAiRunErrorNotFoundApplicationException extends NotFoundApplicationException {
    public ChatAiRunErrorNotFoundApplicationException(String id) {
        super("ChatAiRunError", id);
    }
}
