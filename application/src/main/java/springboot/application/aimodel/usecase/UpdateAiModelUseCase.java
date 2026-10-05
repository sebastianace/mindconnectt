package springboot.application.aimodel.usecase;

import springboot.application.aimodel.command.UpdateAiModelCommand;
import springboot.application.aimodel.dto.AiModelResponse;
import springboot.application.aimodel.exception.AiModelNotFoundApplicationException;
import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.domain.aimodel.model.aggregate.AiModel;
import springboot.domain.aimodel.port.repository.AiModelRepository;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.providermodelai.port.repository.ProviderModelAiRepository;

public class UpdateAiModelUseCase {
    private final AiModelRepository repository;
    private final ProviderModelAiRepository providerModelAiRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdateAiModelUseCase(
            AiModelRepository repository,
            ProviderModelAiRepository providerModelAiRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.providerModelAiRepository = providerModelAiRepository;
        this.eventPublisher = eventPublisher;
    }

    public AiModelResponse execute(UpdateAiModelCommand command) {
        AiModel aggregate = repository.findById(command.id())
                .orElseThrow(() -> new AiModelNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.providerModelId(),
                command.nameModel(),
                command.modelKey(),
                command.inputTokenPrice(),
                command.outputTokenPrice(),
                command.maxTokens(),
                command.contextWindow(),
                command.active());
        AiModel saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return AiModelResponse.from(saved);
    }

    private void validateReferences(UpdateAiModelCommand command) {
        if (!providerModelAiRepository.existsById(command.providerModelId())) {
            throw new ReferenceNotFoundApplicationException("ProviderModelAi", command.providerModelId().value());
        }
    }
}
