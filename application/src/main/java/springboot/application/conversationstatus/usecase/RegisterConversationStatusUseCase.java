package springboot.application.conversationstatus.usecase;

import springboot.application.conversationstatus.command.RegisterConversationStatusCommand;
import springboot.application.conversationstatus.dto.ConversationStatusResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.conversationstatus.model.aggregate.ConversationStatus;
import springboot.domain.conversationstatus.port.repository.ConversationStatusRepository;

public class RegisterConversationStatusUseCase {
    private final ConversationStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterConversationStatusUseCase(
            ConversationStatusRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ConversationStatusResponse execute(RegisterConversationStatusCommand command) {
        ConversationStatus aggregate = ConversationStatus.register(
                command.nameStatus());
        ConversationStatus saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ConversationStatusResponse.from(saved);
    }
}
