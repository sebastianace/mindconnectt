package springboot.application.documenttype.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.documenttype.exception.DocumentTypeNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.documenttype.event.DocumentTypeDeletedEvent;
import springboot.domain.documenttype.model.aggregate.DocumentType;
import springboot.domain.documenttype.model.valueobject.DocumentTypeId;
import springboot.domain.documenttype.port.repository.DocumentTypeRepository;

public class DeleteDocumentTypeUseCase {
    private final DocumentTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteDocumentTypeUseCase(DocumentTypeRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public DocumentTypeDeletedEvent execute(DocumentTypeId id) {
        DocumentType aggregate = repository.findById(id)
                .orElseThrow(() -> new DocumentTypeNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        DocumentTypeDeletedEvent event = new DocumentTypeDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
