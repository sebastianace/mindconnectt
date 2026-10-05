package springboot.application.documenttype.usecase;

import springboot.application.documenttype.command.UpdateDocumentTypeCommand;
import springboot.application.documenttype.dto.DocumentTypeResponse;
import springboot.application.documenttype.exception.DocumentTypeNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.documenttype.model.aggregate.DocumentType;
import springboot.domain.documenttype.port.repository.DocumentTypeRepository;

public class UpdateDocumentTypeUseCase {
    private final DocumentTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateDocumentTypeUseCase(
            DocumentTypeRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public DocumentTypeResponse execute(UpdateDocumentTypeCommand command) {
        DocumentType aggregate = repository.findById(command.id())
                .orElseThrow(() -> new DocumentTypeNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.code(),
                command.name(),
                command.active());
        DocumentType saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return DocumentTypeResponse.from(saved);
    }
}
