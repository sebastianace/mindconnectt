package springboot.application.sendertype.usecase;

import springboot.application.sendertype.dto.SenderTypeResponse;
import springboot.application.sendertype.exception.SenderTypeNotFoundApplicationException;
import springboot.domain.sendertype.model.valueobject.SenderTypeId;
import springboot.domain.sendertype.port.repository.SenderTypeRepository;

public class GetSenderTypeByIdUseCase {
    private final SenderTypeRepository repository;

    public GetSenderTypeByIdUseCase(SenderTypeRepository repository) {
        this.repository = repository;
    }

    public SenderTypeResponse execute(SenderTypeId id) {
        return repository.findById(id)
                .map(SenderTypeResponse::from)
                .orElseThrow(() -> new SenderTypeNotFoundApplicationException(id.value().toString()));
    }
}
