package springboot.application.risklevel.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class RiskLevelNotFoundApplicationException extends NotFoundApplicationException {
    public RiskLevelNotFoundApplicationException(String id) {
        super("RiskLevel", id);
    }
}
