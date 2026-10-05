package springboot.application.chatairunmetric.command;

import java.math.BigDecimal;
import java.util.Objects;

import springboot.domain.chatairun.model.valueobject.ChatAiRunId;

public record RegisterChatAiRunMetricCommand(
        ChatAiRunId aiRunId,
        int promptTokens,
        int completionTokens,
        int totalTokens,
        BigDecimal cost
) {
    public RegisterChatAiRunMetricCommand {
        Objects.requireNonNull(aiRunId, "aiRunId must not be null");
        Objects.requireNonNull(cost, "cost must not be null");
    }
}
