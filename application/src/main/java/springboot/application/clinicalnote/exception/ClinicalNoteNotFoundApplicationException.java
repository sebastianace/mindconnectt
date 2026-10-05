package springboot.application.clinicalnote.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class ClinicalNoteNotFoundApplicationException extends NotFoundApplicationException {
    public ClinicalNoteNotFoundApplicationException(String id) {
        super("ClinicalNote", id);
    }
}
