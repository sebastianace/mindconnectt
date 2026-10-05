package springboot.application.common.exception;

import java.util.UUID;

/**
 * Se lanza cuando un comando referencia (llave foránea) un registro que no existe.
 * El manejador global la traduce a HTTP 422.
 */
public class ReferenceNotFoundApplicationException extends ApplicationException {
    public ReferenceNotFoundApplicationException(String resource, UUID id) {
        super("Referenced " + resource + " not found with id: " + id);
    }
}
