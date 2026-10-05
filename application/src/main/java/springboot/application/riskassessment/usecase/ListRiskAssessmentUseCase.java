package springboot.application.riskassessment.usecase;

import java.util.List;

import springboot.application.riskassessment.dto.RiskAssessmentResponse;
import springboot.domain.riskassessment.port.repository.RiskAssessmentRepository;

public class ListRiskAssessmentUseCase {
    private final RiskAssessmentRepository repository;

    public ListRiskAssessmentUseCase(RiskAssessmentRepository repository) {
        this.repository = repository;
    }

    public List<RiskAssessmentResponse> execute() {
        return repository.findAll().stream()
                .map(RiskAssessmentResponse::from)
                .toList();
    }
}
