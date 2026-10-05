package springboot.application.airunstatus.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class AiRunStatusNotFoundApplicationException extends NotFoundApplicationException {
    public AiRunStatusNotFoundApplicationException(String id) {
        super("AiRunStatus", id);
    }
}
