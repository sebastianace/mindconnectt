package springboot.application.escalationstatus.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class EscalationStatusNotFoundApplicationException extends NotFoundApplicationException {
    public EscalationStatusNotFoundApplicationException(String id) {
        super("EscalationStatus", id);
    }
}
