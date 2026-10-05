package springboot.application.mentalstatusexam.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class MentalStatusExamNotFoundApplicationException extends NotFoundApplicationException {
    public MentalStatusExamNotFoundApplicationException(String id) {
        super("MentalStatusExam", id);
    }
}
