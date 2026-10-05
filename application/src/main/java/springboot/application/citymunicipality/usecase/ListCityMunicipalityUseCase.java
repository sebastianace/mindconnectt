package springboot.application.citymunicipality.usecase;

import java.util.List;

import springboot.application.citymunicipality.dto.CityMunicipalityResponse;
import springboot.domain.citymunicipality.port.repository.CityMunicipalityRepository;

public class ListCityMunicipalityUseCase {
    private final CityMunicipalityRepository repository;

    public ListCityMunicipalityUseCase(CityMunicipalityRepository repository) {
        this.repository = repository;
    }

    public List<CityMunicipalityResponse> execute() {
        return repository.findAll().stream()
                .map(CityMunicipalityResponse::from)
                .toList();
    }
}
