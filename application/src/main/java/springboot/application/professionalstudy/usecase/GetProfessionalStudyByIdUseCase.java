package springboot.application.professionalstudy.usecase;

import springboot.application.professionalstudy.dto.ProfessionalStudyResponse;
import springboot.application.professionalstudy.exception.ProfessionalStudyNotFoundApplicationException;
import springboot.domain.professionalstudy.model.valueobject.ProfessionalStudyId;
import springboot.domain.professionalstudy.port.repository.ProfessionalStudyRepository;

public class GetProfessionalStudyByIdUseCase {
    private final ProfessionalStudyRepository repository;

    public GetProfessionalStudyByIdUseCase(ProfessionalStudyRepository repository) {
        this.repository = repository;
    }

    public ProfessionalStudyResponse execute(ProfessionalStudyId id) {
        return repository.findById(id)
                .map(ProfessionalStudyResponse::from)
                .orElseThrow(() -> new ProfessionalStudyNotFoundApplicationException(id.value().toString()));
    }
}
