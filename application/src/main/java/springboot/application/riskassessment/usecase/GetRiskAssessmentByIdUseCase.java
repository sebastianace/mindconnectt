package springboot.application.riskassessment.usecase;

import springboot.application.riskassessment.dto.RiskAssessmentResponse;
import springboot.application.riskassessment.exception.RiskAssessmentNotFoundApplicationException;
import springboot.domain.riskassessment.model.valueobject.RiskAssessmentId;
import springboot.domain.riskassessment.port.repository.RiskAssessmentRepository;

public class GetRiskAssessmentByIdUseCase {
    private final RiskAssessmentRepository repository;

    public GetRiskAssessmentByIdUseCase(RiskAssessmentRepository repository) {
        this.repository = repository;
    }

    public RiskAssessmentResponse execute(RiskAssessmentId id) {
        return repository.findById(id)
                .map(RiskAssessmentResponse::from)
                .orElseThrow(() -> new RiskAssessmentNotFoundApplicationException(id.value().toString()));
    }
}
