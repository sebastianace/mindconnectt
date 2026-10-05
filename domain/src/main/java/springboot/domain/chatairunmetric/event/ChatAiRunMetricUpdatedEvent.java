package springboot.domain.chatairunmetric.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.chatairun.model.valueobject.ChatAiRunId;
import springboot.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
import springboot.domain.common.event.DomainEvent;

public record ChatAiRunMetricUpdatedEvent(
        ChatAiRunMetricId id,
        ChatAiRunId aiRunId,
        int promptTokens,
        int completionTokens,
        int totalTokens,
        BigDecimal cost,
        LocalDateTime occurredOn
) implements DomainEvent {
    public ChatAiRunMetricUpdatedEvent {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(aiRunId, "aiRunId must not be null");
        Objects.requireNonNull(cost, "cost must not be null");
        Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }
}
