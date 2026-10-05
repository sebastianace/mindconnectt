package springboot.application.aimodel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.aimodel.model.aggregate.AiModel;

public record AiModelResponse(
        UUID id,
        UUID providerModelId,
        String nameModel,
        String modelKey,
        BigDecimal inputTokenPrice,
        BigDecimal outputTokenPrice,
        int maxTokens,
        int contextWindow,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static AiModelResponse from(AiModel aggregate) {
        return new AiModelResponse(
                aggregate.id().value(),
                aggregate.providerModelId().value(),
                aggregate.nameModel(),
                aggregate.modelKey(),
                aggregate.inputTokenPrice(),
                aggregate.outputTokenPrice(),
                aggregate.maxTokens(),
                aggregate.contextWindow(),
                aggregate.active(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}
