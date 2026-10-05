package springboot.application.citymunicipality.usecase;

import springboot.application.citymunicipality.dto.CityMunicipalityResponse;
import springboot.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import springboot.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import springboot.domain.citymunicipality.port.repository.CityMunicipalityRepository;

public class GetCityMunicipalityByIdUseCase {
    private final CityMunicipalityRepository repository;

    public GetCityMunicipalityByIdUseCase(CityMunicipalityRepository repository) {
        this.repository = repository;
    }

    public CityMunicipalityResponse execute(CityMunicipalityId id) {
        return repository.findById(id)
                .map(CityMunicipalityResponse::from)
                .orElseThrow(() -> new CityMunicipalityNotFoundApplicationException(id.value().toString()));
    }
}
