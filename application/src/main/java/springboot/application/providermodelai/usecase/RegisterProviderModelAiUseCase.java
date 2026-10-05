package springboot.application.providermodelai.usecase;

import springboot.application.providermodelai.command.RegisterProviderModelAiCommand;
import springboot.application.providermodelai.dto.ProviderModelAiResponse;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.providermodelai.model.aggregate.ProviderModelAi;
import springboot.domain.providermodelai.port.repository.ProviderModelAiRepository;

public class RegisterProviderModelAiUseCase {
    private final ProviderModelAiRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterProviderModelAiUseCase(
            ProviderModelAiRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ProviderModelAiResponse execute(RegisterProviderModelAiCommand command) {
        ProviderModelAi aggregate = ProviderModelAi.register(
                command.nameProviderAi(),
                command.razonSocial(),
                command.sitioWeb(),
                command.active());
        ProviderModelAi saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ProviderModelAiResponse.from(saved);
    }
}
