package springboot.application.diagnosticsystem.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class DiagnosticSystemNotFoundApplicationException extends NotFoundApplicationException {
    public DiagnosticSystemNotFoundApplicationException(String id) {
        super("DiagnosticSystem", id);
    }
}
