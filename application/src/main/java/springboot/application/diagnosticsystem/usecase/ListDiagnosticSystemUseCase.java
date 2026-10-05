package springboot.application.diagnosticsystem.usecase;

import java.util.List;

import springboot.application.diagnosticsystem.dto.DiagnosticSystemResponse;
import springboot.domain.diagnosticsystem.port.repository.DiagnosticSystemRepository;

public class ListDiagnosticSystemUseCase {
    private final DiagnosticSystemRepository repository;

    public ListDiagnosticSystemUseCase(DiagnosticSystemRepository repository) {
        this.repository = repository;
    }

    public List<DiagnosticSystemResponse> execute() {
        return repository.findAll().stream()
                .map(DiagnosticSystemResponse::from)
                .toList();
    }
}
