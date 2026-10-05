package springboot.application.encounter.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class EncounterNotFoundApplicationException extends NotFoundApplicationException {
    public EncounterNotFoundApplicationException(String id) {
        super("Encounter", id);
    }
}
