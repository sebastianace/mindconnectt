package springboot.application.diagnosticsystem.usecase;

import springboot.application.diagnosticsystem.dto.DiagnosticSystemResponse;
import springboot.application.diagnosticsystem.exception.DiagnosticSystemNotFoundApplicationException;
import springboot.domain.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import springboot.domain.diagnosticsystem.port.repository.DiagnosticSystemRepository;

public class GetDiagnosticSystemByIdUseCase {
    private final DiagnosticSystemRepository repository;

    public GetDiagnosticSystemByIdUseCase(DiagnosticSystemRepository repository) {
        this.repository = repository;
    }

    public DiagnosticSystemResponse execute(DiagnosticSystemId id) {
        return repository.findById(id)
                .map(DiagnosticSystemResponse::from)
                .orElseThrow(() -> new DiagnosticSystemNotFoundApplicationException(id.value().toString()));
    }
}
