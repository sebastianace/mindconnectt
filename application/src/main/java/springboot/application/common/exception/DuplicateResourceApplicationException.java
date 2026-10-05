package springboot.application.common.exception;

/**
 * Se lanza cuando se intenta registrar un valor que debe ser único y ya existe.
 * El manejador global la traduce a HTTP 409.
 */
public class DuplicateResourceApplicationException extends ApplicationException {
    public DuplicateResourceApplicationException(String resource, String field, String value) {
        super(resource + " with " + field + " '" + value + "' already exists");
    }
}
