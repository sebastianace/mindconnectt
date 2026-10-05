package springboot.application.documenttype.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class DocumentTypeNotFoundApplicationException extends NotFoundApplicationException {
    public DocumentTypeNotFoundApplicationException(String id) {
        super("DocumentType", id);
    }
}
