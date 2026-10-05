package springboot.application.study.usecase;

import springboot.application.study.dto.StudyResponse;
import springboot.application.study.exception.StudyNotFoundApplicationException;
import springboot.domain.study.model.valueobject.StudyId;
import springboot.domain.study.port.repository.StudyRepository;

public class GetStudyByIdUseCase {
    private final StudyRepository repository;

    public GetStudyByIdUseCase(StudyRepository repository) {
        this.repository = repository;
    }

    public StudyResponse execute(StudyId id) {
        return repository.findById(id)
                .map(StudyResponse::from)
                .orElseThrow(() -> new StudyNotFoundApplicationException(id.value().toString()));
    }
}
