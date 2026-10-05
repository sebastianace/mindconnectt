package springboot.application.chatmessage.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ChatMessageNotFoundApplicationException extends NotFoundApplicationException {
    public ChatMessageNotFoundApplicationException(String id) {
        super("ChatMessage", id);
    }
}
