package springboot.application.clinicalrecord.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import springboot.domain.clinicalrecord.model.aggregate.ClinicalRecord;

public record ClinicalRecordResponse(
        UUID id,
        UUID patientId,
        LocalDateTime creationDate,
        String recordNumber,
        LocalDateTime openedAt,
        LocalDateTime closedAt,
        UUID statusId,
        UUID createdBy,
        LocalDateTime createdAt
) {
    public static ClinicalRecordResponse from(ClinicalRecord aggregate) {
        return new ClinicalRecordResponse(
                aggregate.id().value(),
                aggregate.patientId().value(),
                aggregate.creationDate(),
                aggregate.recordNumber(),
                aggregate.openedAt(),
                aggregate.closedAt(),
                aggregate.statusId().value(),
                aggregate.createdBy().value(),
                aggregate.createdAt());
    }
}
