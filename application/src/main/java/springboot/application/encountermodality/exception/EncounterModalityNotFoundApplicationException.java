package springboot.application.encountermodality.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class EncounterModalityNotFoundApplicationException extends NotFoundApplicationException {
    public EncounterModalityNotFoundApplicationException(String id) {
        super("EncounterModality", id);
    }
}
