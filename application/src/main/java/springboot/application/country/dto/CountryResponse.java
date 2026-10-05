package springboot.application.country.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.country.model.aggregate.Country;

public record CountryResponse(
        UUID id,
        String nameCountry,
        String codeCountry,
        String description,
        boolean active,
        String telephonePrefix,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CountryResponse from(Country aggregate) {
        return new CountryResponse(
                aggregate.id().value(),
                aggregate.nameCountry(),
                aggregate.codeCountry(),
                aggregate.description(),
                aggregate.active(),
                aggregate.telephonePrefix(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}
