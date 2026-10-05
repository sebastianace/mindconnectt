package springboot.application.country.usecase;

import springboot.application.country.dto.CountryResponse;
import springboot.application.country.exception.CountryNotFoundApplicationException;
import springboot.domain.country.model.valueobject.CountryId;
import springboot.domain.country.port.repository.CountryRepository;

public class GetCountryByIdUseCase {
    private final CountryRepository repository;

    public GetCountryByIdUseCase(CountryRepository repository) {
        this.repository = repository;
    }

    public CountryResponse execute(CountryId id) {
        return repository.findById(id)
                .map(CountryResponse::from)
                .orElseThrow(() -> new CountryNotFoundApplicationException(id.value().toString()));
    }
}
