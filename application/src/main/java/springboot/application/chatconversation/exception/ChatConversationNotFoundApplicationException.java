package springboot.application.chatconversation.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ChatConversationNotFoundApplicationException extends NotFoundApplicationException {
    public ChatConversationNotFoundApplicationException(String id) {
        super("ChatConversation", id);
    }
}
