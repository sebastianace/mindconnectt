package springboot.application.sendertype.usecase;

import springboot.application.sendertype.command.RegisterSenderTypeCommand;
import springboot.application.sendertype.dto.SenderTypeResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.sendertype.model.aggregate.SenderType;
import springboot.domain.sendertype.port.repository.SenderTypeRepository;

public class RegisterSenderTypeUseCase {
    private final SenderTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterSenderTypeUseCase(
            SenderTypeRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public SenderTypeResponse execute(RegisterSenderTypeCommand command) {
        SenderType aggregate = SenderType.register(
                command.nameType());
        SenderType saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return SenderTypeResponse.from(saved);
    }
}
