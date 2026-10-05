package springboot.application.diagnosticsystem.usecase;

import springboot.application.diagnosticsystem.command.UpdateDiagnosticSystemCommand;
import springboot.application.diagnosticsystem.dto.DiagnosticSystemResponse;
import springboot.application.diagnosticsystem.exception.DiagnosticSystemNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.diagnosticsystem.model.aggregate.DiagnosticSystem;
import springboot.domain.diagnosticsystem.port.repository.DiagnosticSystemRepository;

public class UpdateDiagnosticSystemUseCase {
    private final DiagnosticSystemRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateDiagnosticSystemUseCase(
            DiagnosticSystemRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public DiagnosticSystemResponse execute(UpdateDiagnosticSystemCommand command) {
        DiagnosticSystem aggregate = repository.findById(command.id())
                .orElseThrow(() -> new DiagnosticSystemNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
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
