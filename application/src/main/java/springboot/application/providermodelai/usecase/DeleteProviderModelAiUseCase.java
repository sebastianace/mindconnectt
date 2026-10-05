package springboot.application.providermodelai.usecase;

import java.time.LocalDateTime;
import java.util.List;

import springboot.application.providermodelai.exception.ProviderModelAiNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.providermodelai.event.ProviderModelAiDeletedEvent;
import springboot.domain.providermodelai.model.aggregate.ProviderModelAi;
import springboot.domain.providermodelai.model.valueobject.ProviderModelAiId;
import springboot.domain.providermodelai.port.repository.ProviderModelAiRepository;

public class DeleteProviderModelAiUseCase {
    private final ProviderModelAiRepository repository;
    private final DomainEventPublisher eventPublisher;

    public DeleteProviderModelAiUseCase(ProviderModelAiRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ProviderModelAiDeletedEvent execute(ProviderModelAiId id) {
        ProviderModelAi aggregate = repository.findById(id)
                .orElseThrow(() -> new ProviderModelAiNotFoundApplicationException(id.value().toString()));
        repository.delete(aggregate);
        ProviderModelAiDeletedEvent event = new ProviderModelAiDeletedEvent(id, LocalDateTime.now());
        eventPublisher.publish(List.of(event));
        return event;
    }
}
