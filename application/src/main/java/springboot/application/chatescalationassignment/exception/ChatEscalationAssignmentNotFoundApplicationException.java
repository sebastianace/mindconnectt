package springboot.application.chatescalationassignment.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ChatEscalationAssignmentNotFoundApplicationException extends NotFoundApplicationException {
    public ChatEscalationAssignmentNotFoundApplicationException(String id) {
        super("ChatEscalationAssignment", id);
    }
}
