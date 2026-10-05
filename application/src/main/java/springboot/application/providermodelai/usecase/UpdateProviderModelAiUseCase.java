package springboot.application.providermodelai.usecase;

import springboot.application.providermodelai.command.UpdateProviderModelAiCommand;
import springboot.application.providermodelai.dto.ProviderModelAiResponse;
import springboot.application.providermodelai.exception.ProviderModelAiNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.providermodelai.model.aggregate.ProviderModelAi;
import springboot.domain.providermodelai.port.repository.ProviderModelAiRepository;

public class UpdateProviderModelAiUseCase {
    private final ProviderModelAiRepository repository;
    private final DomainEventPublisher eventPublisher;

    public UpdateProviderModelAiUseCase(
            ProviderModelAiRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ProviderModelAiResponse execute(UpdateProviderModelAiCommand command) {
        ProviderModelAi aggregate = repository.findById(command.id())
                .orElseThrow(() -> new ProviderModelAiNotFoundApplicationException(command.id().value().toString()));
        aggregate.update(
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
