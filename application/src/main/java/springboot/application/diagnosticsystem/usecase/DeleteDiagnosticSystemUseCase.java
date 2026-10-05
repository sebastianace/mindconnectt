package springboot.application.diagnosticsystem.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.diagnosticsystem.exception.DiagnosticSystemNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.diagnosticsystem.event.DiagnosticSystemDeletedEvent;
import springboot.domain.diagnosticsystem.model.aggregate.DiagnosticSystem;
import springboot.domain.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import springboot.domain.diagnosticsystem.port.repository.DiagnosticSystemRepository;

public class DeleteDiagnosticSystemUseCase {
    private final DiagnosticSystemRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteDiagnosticSystemUseCase(DiagnosticSystemRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public DiagnosticSystemDeletedEvent execute(DiagnosticSystemId id) {
        DiagnosticSystem aggregate = repository.findById(id)
                .orElseThrow(() -> new DiagnosticSystemNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        DiagnosticSystemDeletedEvent event = new DiagnosticSystemDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
