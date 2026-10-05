package springboot.application.aimodel.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class AiModelNotFoundApplicationException extends NotFoundApplicationException {
    public AiModelNotFoundApplicationException(String id) {
        super("AiModel", id);
    }
}
