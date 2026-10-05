package springboot.application.country.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class CountryNotFoundApplicationException extends NotFoundApplicationException {
    public CountryNotFoundApplicationException(String id) {
        super("Country", id);
    }
}
