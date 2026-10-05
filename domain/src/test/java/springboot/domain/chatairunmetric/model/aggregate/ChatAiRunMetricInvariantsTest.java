package springboot.domain.chatairunmetric.model.aggregate;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import springboot.domain.chatairun.model.valueobject.ChatAiRunId;
import springboot.domain.common.exception.DomainValidationException;

class ChatAiRunMetricInvariantsTest {
    @Test void shouldRejectTotalThatDoesNotMatchTheSum() {
        assertThrows(DomainValidationException.class,
                () -> ChatAiRunMetric.register(ChatAiRunId.generate(), 100, 50, 999, new BigDecimal("0.0015")));
    }

    @Test void shouldRejectNegativeValues() {
        assertThrows(DomainValidationException.class,
                () -> ChatAiRunMetric.register(ChatAiRunId.generate(), -1, 1, 0, BigDecimal.ZERO));
        assertThrows(DomainValidationException.class,
                () -> ChatAiRunMetric.register(ChatAiRunId.generate(), 1, 1, 2, new BigDecimal("-1")));
    }

    @Test void shouldRejectInvalidUpdate() {
        ChatAiRunMetric metric = ChatAiRunMetric.register(ChatAiRunId.generate(), 10, 5, 15, BigDecimal.ONE);
        assertThrows(DomainValidationException.class,
                () -> metric.update(metric.aiRunId(), 10, 5, 16, BigDecimal.ONE));
    }
}
