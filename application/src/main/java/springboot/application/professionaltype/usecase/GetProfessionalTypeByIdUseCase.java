package springboot.application.professionaltype.usecase;

import springboot.application.professionaltype.dto.ProfessionalTypeResponse;
import springboot.application.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import springboot.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import springboot.domain.professionaltype.port.repository.ProfessionalTypeRepository;

public class GetProfessionalTypeByIdUseCase {
    private final ProfessionalTypeRepository repository;

    public GetProfessionalTypeByIdUseCase(ProfessionalTypeRepository repository) {
        this.repository = repository;
    }

    public ProfessionalTypeResponse execute(ProfessionalTypeId id) {
        return repository.findById(id)
                .map(ProfessionalTypeResponse::from)
                .orElseThrow(() -> new ProfessionalTypeNotFoundApplicationException(id.value().toString()));
    }
}
