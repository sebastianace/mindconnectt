package springboot.application.chatescalationstatushistory.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ChatEscalationStatusHistoryNotFoundApplicationException extends NotFoundApplicationException {
    public ChatEscalationStatusHistoryNotFoundApplicationException(String id) {
        super("ChatEscalationStatusHistory", id);
    }
}
