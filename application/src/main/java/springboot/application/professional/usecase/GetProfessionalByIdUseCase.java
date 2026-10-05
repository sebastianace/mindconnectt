package springboot.application.professional.usecase;

import springboot.application.professional.dto.ProfessionalResponse;
import springboot.application.professional.exception.ProfessionalNotFoundApplicationException;
import springboot.domain.professional.model.valueobject.ProfessionalId;
import springboot.domain.professional.port.repository.ProfessionalRepository;

public class GetProfessionalByIdUseCase {
    private final ProfessionalRepository repository;

    public GetProfessionalByIdUseCase(ProfessionalRepository repository) {
        this.repository = repository;
    }

    public ProfessionalResponse execute(ProfessionalId id) {
        return repository.findById(id)
                .map(ProfessionalResponse::from)
                .orElseThrow(() -> new ProfessionalNotFoundApplicationException(id.value().toString()));
    }
}
