package springboot.application.diagnosticsystem.usecase;

import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.application.diagnosticsystem.command.RegisterDiagnosticSystemCommand;
import springboot.application.diagnosticsystem.dto.DiagnosticSystemResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.diagnosticsystem.model.aggregate.DiagnosticSystem;
import springboot.domain.diagnosticsystem.port.repository.DiagnosticSystemRepository;

public class RegisterDiagnosticSystemUseCase {
    private final DiagnosticSystemRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterDiagnosticSystemUseCase(
            DiagnosticSystemRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public DiagnosticSystemResponse execute(RegisterDiagnosticSystemCommand command) {
        if (repository.existsByCode(command.code())) {
            throw new DuplicateResourceApplicationException("DiagnosticSystem", "code", command.code());
        }
        DiagnosticSystem aggregate = DiagnosticSystem.register(
                command.code(),
                command.name(),
                command.active(),
                command.version());
        DiagnosticSystem saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return DiagnosticSystemResponse.from(saved);
    }
}
