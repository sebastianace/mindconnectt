package springboot.application.stateregion.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.stateregion.model.aggregate.StateRegion;

public record StateRegionResponse(
        UUID id,
        String nameRegion,
        String codeRegion,
        String description,
        boolean active,
        UUID countryId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static StateRegionResponse from(StateRegion aggregate) {
        return new StateRegionResponse(
                aggregate.id().value(),
                aggregate.nameRegion(),
                aggregate.codeRegion(),
                aggregate.description(),
                aggregate.active(),
                aggregate.countryId().value(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}
