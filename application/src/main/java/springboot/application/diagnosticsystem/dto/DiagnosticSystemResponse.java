package springboot.application.diagnosticsystem.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.diagnosticsystem.model.aggregate.DiagnosticSystem;

public record DiagnosticSystemResponse(
        UUID id,
        String code,
        String name,
        boolean active,
        String version,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static DiagnosticSystemResponse from(DiagnosticSystem aggregate) {
        return new DiagnosticSystemResponse(
                aggregate.id().value(),
                aggregate.code(),
                aggregate.name(),
                aggregate.active(),
                aggregate.version(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}
