package springboot.application.assessmenttype.usecase;

import springboot.application.assessmenttype.dto.AssessmentTypeResponse;
import springboot.application.assessmenttype.exception.AssessmentTypeNotFoundApplicationException;
import springboot.domain.assessmenttype.model.valueobject.AssessmentTypeId;
import springboot.domain.assessmenttype.port.repository.AssessmentTypeRepository;

public class GetAssessmentTypeByIdUseCase {
    private final AssessmentTypeRepository repository;

    public GetAssessmentTypeByIdUseCase(AssessmentTypeRepository repository) {
        this.repository = repository;
    }

    public AssessmentTypeResponse execute(AssessmentTypeId id) {
        return repository.findById(id)
                .map(AssessmentTypeResponse::from)
                .orElseThrow(() -> new AssessmentTypeNotFoundApplicationException(id.value().toString()));
    }
}
