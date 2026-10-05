package springboot.application.escalationstatus.usecase;

import springboot.application.escalationstatus.dto.EscalationStatusResponse;
import springboot.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;
import springboot.domain.escalationstatus.model.valueobject.EscalationStatusId;
import springboot.domain.escalationstatus.port.repository.EscalationStatusRepository;

public class GetEscalationStatusByIdUseCase {
    private final EscalationStatusRepository repository;

    public GetEscalationStatusByIdUseCase(EscalationStatusRepository repository) {
        this.repository = repository;
    }

    public EscalationStatusResponse execute(EscalationStatusId id) {
        return repository.findById(id)
                .map(EscalationStatusResponse::from)
                .orElseThrow(() -> new EscalationStatusNotFoundApplicationException(id.value().toString()));
    }
}
