package springboot.infrastructure.chatairunmetric.adapters.in.rest.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateChatAiRunMetricRequest(
        @NotNull(message = "aiRunId is required")
        UUID aiRunId,

        @NotNull(message = "promptTokens is required")
        @PositiveOrZero(message = "promptTokens must be greater than or equal to 0")
        Integer promptTokens,

        @NotNull(message = "completionTokens is required")
        @PositiveOrZero(message = "completionTokens must be greater than or equal to 0")
        Integer completionTokens,

        @NotNull(message = "totalTokens is required")
        @PositiveOrZero(message = "totalTokens must be greater than or equal to 0")
        Integer totalTokens,

        @NotNull(message = "cost is required")
        @Digits(integer = 4, fraction = 6, message = "cost must fit DECIMAL(10,6)")
        @PositiveOrZero(message = "cost must be greater than or equal to 0")
        BigDecimal cost
) {
}
