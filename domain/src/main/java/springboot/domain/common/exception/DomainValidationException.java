package springboot.domain.common.exception;

/**
 * Se lanza cuando un agregado detecta que se viola una regla de negocio (invariante).
 */
public class DomainValidationException extends RuntimeException {
    public DomainValidationException(String message) {
        super(message);
    }
}
