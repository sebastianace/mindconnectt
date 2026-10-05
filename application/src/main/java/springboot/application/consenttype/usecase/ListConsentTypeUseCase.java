package springboot.application.consenttype.usecase;

import java.util.List;

import springboot.application.consenttype.dto.ConsentTypeResponse;
import springboot.domain.consenttype.port.repository.ConsentTypeRepository;

public class ListConsentTypeUseCase {
    private final ConsentTypeRepository repository;

    public ListConsentTypeUseCase(ConsentTypeRepository repository) {
        this.repository = repository;
    }

    public List<ConsentTypeResponse> execute() {
        return repository.findAll().stream()
                .map(ConsentTypeResponse::from)
                .toList();
    }
}
