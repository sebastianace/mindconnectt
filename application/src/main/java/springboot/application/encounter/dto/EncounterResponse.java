package springboot.application.encounter.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.encounter.model.aggregate.Encounter;

public record EncounterResponse(
        UUID id,
        UUID clinicalRecordId,
        UUID professionalId,
        UUID encounterTypeId,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        String reasonForVisit,
        String currentCondition,
        UUID modalityId,
        UUID statusId,
        UUID createdBy,
        UUID updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static EncounterResponse from(Encounter aggregate) {
        return new EncounterResponse(
                aggregate.id().value(),
                aggregate.clinicalRecordId().value(),
                aggregate.professionalId().value(),
                aggregate.encounterTypeId().value(),
                aggregate.startedAt(),
                aggregate.endedAt(),
                aggregate.reasonForVisit(),
                aggregate.currentCondition(),
                aggregate.modalityId().value(),
                aggregate.statusId().value(),
                aggregate.createdBy().value(),
                aggregate.updatedBy().value(),
                aggregate.createdAt(),
                aggregate.updatedAt());
    }
}
