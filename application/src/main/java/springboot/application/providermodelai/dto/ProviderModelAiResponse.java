package springboot.application.providermodelai.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.providermodelai.model.aggregate.ProviderModelAi;

public record ProviderModelAiResponse(
        UUID id,
        String nameProviderAi,
        String razonSocial,
        String sitioWeb,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ProviderModelAiResponse from(ProviderModelAi aggregate) {
        return new ProviderModelAiResponse(
                aggregate.id().value(),
                aggregate.nameProviderAi(),
                aggregate.razonSocial(),
                aggregate.sitioWeb(),
                aggregate.active(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}
