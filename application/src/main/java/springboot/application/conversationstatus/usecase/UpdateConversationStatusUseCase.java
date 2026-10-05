package springboot.application.conversationstatus.usecase;

import springboot.application.conversationstatus.command.UpdateConversationStatusCommand;
import springboot.application.conversationstatus.dto.ConversationStatusResponse;
import springboot.application.conversationstatus.exception.ConversationStatusNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.conversationstatus.model.aggregate.ConversationStatus;
import springboot.domain.conversationstatus.port.repository.ConversationStatusRepository;

public class UpdateConversationStatusUseCase {
    private final ConversationStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateConversationStatusUseCase(
            ConversationStatusRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ConversationStatusResponse execute(UpdateConversationStatusCommand command) {
        ConversationStatus aggregate = repository.findById(command.id())
                .orElseThrow(() -> new ConversationStatusNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
                command.nameStatus());
        ConversationStatus saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ConversationStatusResponse.from(saved);
    }
}
