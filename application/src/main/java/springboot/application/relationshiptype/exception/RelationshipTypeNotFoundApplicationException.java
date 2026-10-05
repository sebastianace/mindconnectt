package springboot.application.relationshiptype.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class RelationshipTypeNotFoundApplicationException extends NotFoundApplicationException {
    public RelationshipTypeNotFoundApplicationException(String id) {
        super("RelationshipType", id);
    }
}
