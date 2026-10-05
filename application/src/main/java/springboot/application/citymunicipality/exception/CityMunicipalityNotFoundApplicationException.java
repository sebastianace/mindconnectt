package springboot.application.citymunicipality.exception;

import springboot.application.common.exception.NotFoundApplicationException;

public class CityMunicipalityNotFoundApplicationException extends NotFoundApplicationException {
    public CityMunicipalityNotFoundApplicationException(String id) {
        super("CityMunicipality", id);
    }
}
