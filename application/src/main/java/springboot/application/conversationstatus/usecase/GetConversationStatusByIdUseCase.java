package springboot.application.conversationstatus.usecase;

import springboot.application.conversationstatus.dto.ConversationStatusResponse;
import springboot.application.conversationstatus.exception.ConversationStatusNotFoundApplicationException;
import springboot.domain.conversationstatus.model.valueobject.ConversationStatusId;
import springboot.domain.conversationstatus.port.repository.ConversationStatusRepository;

public class GetConversationStatusByIdUseCase {
    private final ConversationStatusRepository repository;

    public GetConversationStatusByIdUseCase(ConversationStatusRepository repository) {
        this.repository = repository;
    }

    public ConversationStatusResponse execute(ConversationStatusId id) {
        return repository.findById(id)
                .map(ConversationStatusResponse::from)
                .orElseThrow(() -> new ConversationStatusNotFoundApplicationException(id.value().toString()));
    }
}
