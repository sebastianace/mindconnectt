package springboot.application.citymunicipality.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.citymunicipality.model.aggregate.CityMunicipality;

public record CityMunicipalityResponse(
        UUID id,
        String nameCity,
        String codeCity,
        String description,
        boolean active,
        UUID regionId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CityMunicipalityResponse from(CityMunicipality aggregate) {
        return new CityMunicipalityResponse(
                aggregate.id().value(),
                aggregate.nameCity(),
                aggregate.codeCity(),
                aggregate.description(),
                aggregate.active(),
                aggregate.regionId().value(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}
