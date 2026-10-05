package springboot.application.study.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class StudyNotFoundApplicationException extends NotFoundApplicationException {
    public StudyNotFoundApplicationException(String id) {
        super("Study", id);
    }
}
