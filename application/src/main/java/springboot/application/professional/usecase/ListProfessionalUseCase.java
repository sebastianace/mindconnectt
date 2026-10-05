package springboot.application.professional.usecase;

import java.util.List;

import springboot.application.professional.dto.ProfessionalResponse;
import springboot.domain.professional.port.repository.ProfessionalRepository;

public class ListProfessionalUseCase {
    private final ProfessionalRepository repository;

    public ListProfessionalUseCase(ProfessionalRepository repository) {
        this.repository = repository;
    }

    public List<ProfessionalResponse> execute() {
        return repository.findAll().stream()
                .map(ProfessionalResponse::from)
                .toList();
    }
}
