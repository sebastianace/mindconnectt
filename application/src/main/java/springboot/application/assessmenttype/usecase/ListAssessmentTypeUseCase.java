package springboot.application.assessmenttype.usecase;

import java.util.List;

import springboot.application.assessmenttype.dto.AssessmentTypeResponse;
import springboot.domain.assessmenttype.port.repository.AssessmentTypeRepository;

public class ListAssessmentTypeUseCase {
    private final AssessmentTypeRepository repository;

    public ListAssessmentTypeUseCase(AssessmentTypeRepository repository) {
        this.repository = repository;
    }

    public List<AssessmentTypeResponse> execute() {
        return repository.findAll().stream()
                .map(AssessmentTypeResponse::from)
                .toList();
    }
}
