package springboot.application.encounterstatus.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class EncounterStatusNotFoundApplicationException extends NotFoundApplicationException {
    public EncounterStatusNotFoundApplicationException(String id) {
        super("EncounterStatus", id);
    }
}
