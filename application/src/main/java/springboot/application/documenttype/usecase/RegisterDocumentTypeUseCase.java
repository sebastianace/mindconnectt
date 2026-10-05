package springboot.application.documenttype.usecase;

import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.application.documenttype.command.RegisterDocumentTypeCommand;
import springboot.application.documenttype.dto.DocumentTypeResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.documenttype.model.aggregate.DocumentType;
import springboot.domain.documenttype.port.repository.DocumentTypeRepository;

public class RegisterDocumentTypeUseCase {
    private final DocumentTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterDocumentTypeUseCase(
            DocumentTypeRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public DocumentTypeResponse execute(RegisterDocumentTypeCommand command) {
        if (repository.existsByCode(command.code())) {
            throw new DuplicateResourceApplicationException("DocumentType", "code", command.code());
        }
        DocumentType aggregate = DocumentType.register(
                command.code(),
                command.name(),
                command.active());
        DocumentType saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return DocumentTypeResponse.from(saved);
    }
}
