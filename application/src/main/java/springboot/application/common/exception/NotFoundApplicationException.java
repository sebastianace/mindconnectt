package springboot.application.common.exception;

/**
 * Base de todas las excepciones "recurso no encontrado".
 * El manejador global la traduce a HTTP 404.
 */
public abstract class NotFoundApplicationException extends ApplicationException {
    protected NotFoundApplicationException(String resource, String id) {
        super(resource + " not found with id: " + id);
    }
}
