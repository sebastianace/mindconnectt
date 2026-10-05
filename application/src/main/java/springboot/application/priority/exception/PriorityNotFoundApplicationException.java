package springboot.application.priority.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class PriorityNotFoundApplicationException extends NotFoundApplicationException {
    public PriorityNotFoundApplicationException(String id) {
        super("Priority", id);
    }
}
