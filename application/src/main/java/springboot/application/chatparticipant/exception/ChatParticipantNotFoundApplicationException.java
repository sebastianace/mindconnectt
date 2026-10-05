package springboot.application.chatparticipant.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ChatParticipantNotFoundApplicationException extends NotFoundApplicationException {
    public ChatParticipantNotFoundApplicationException(String id) {
        super("ChatParticipant", id);
    }
}
