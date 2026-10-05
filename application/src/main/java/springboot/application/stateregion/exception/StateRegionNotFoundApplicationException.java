package springboot.application.stateregion.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class StateRegionNotFoundApplicationException extends NotFoundApplicationException {
    public StateRegionNotFoundApplicationException(String id) {
        super("StateRegion", id);
    }
}
