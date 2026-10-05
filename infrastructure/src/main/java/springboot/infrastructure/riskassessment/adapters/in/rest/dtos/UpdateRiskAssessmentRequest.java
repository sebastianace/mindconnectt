package springboot.infrastructure.riskassessment.adapters.in.rest.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateRiskAssessmentRequest(
        @NotNull(message = "encounterId is required")
        UUID encounterId,

        @NotNull(message = "riskLevelId is required")
        UUID riskLevelId,

        @NotNull(message = "suicidalIdeation is required")
        Boolean suicidalIdeation,

        @NotNull(message = "suicidePlan is required")
        Boolean suicidePlan,

        @NotNull(message = "suicideIntent is required")
        Boolean suicideIntent,

        @NotNull(message = "selfHarm is required")
        Boolean selfHarm,

        @NotNull(message = "harmToOthers is required")
        Boolean harmToOthers,

        @NotBlank(message = "riskFactors is required")
        String riskFactors,

        @NotBlank(message = "protectiveFactors is required")
        String protectiveFactors,

        @NotBlank(message = "clinicalActions is required")
        String clinicalActions,

        @NotBlank(message = "observations is required")
        String observations,

        @NotNull(message = "assessedAt is required")
        LocalDateTime assessedAt,

        @NotNull(message = "assessedBy is required")
        UUID assessedBy
) {
}
