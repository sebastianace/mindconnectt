package springboot.infrastructure.aimodel.adapters.in.rest.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateAiModelRequest(
        @NotNull(message = "providerModelId is required")
        UUID providerModelId,

        @NotBlank(message = "nameModel is required")
        @Size(max = 100, message = "nameModel must have at most 100 characters")
        String nameModel,

        @NotBlank(message = "modelKey is required")
        @Size(max = 120, message = "modelKey must have at most 120 characters")
        String modelKey,

        @NotNull(message = "inputTokenPrice is required")
        @Digits(integer = 4, fraction = 8, message = "inputTokenPrice must fit DECIMAL(12,8)")
        @PositiveOrZero(message = "inputTokenPrice must be greater than or equal to 0")
        BigDecimal inputTokenPrice,

        @NotNull(message = "outputTokenPrice is required")
        @Digits(integer = 4, fraction = 8, message = "outputTokenPrice must fit DECIMAL(12,8)")
        @PositiveOrZero(message = "outputTokenPrice must be greater than or equal to 0")
        BigDecimal outputTokenPrice,

        @NotNull(message = "maxTokens is required")
        @Positive(message = "maxTokens must be greater than 0")
        Integer maxTokens,

        @NotNull(message = "contextWindow is required")
        @Positive(message = "contextWindow must be greater than 0")
        Integer contextWindow,

        @NotNull(message = "active is required")
        Boolean active
) {
}
