package springboot.application.encountertype.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class EncounterTypeNotFoundApplicationException extends NotFoundApplicationException {
    public EncounterTypeNotFoundApplicationException(String id) {
        super("EncounterType", id);
    }
}
