package springboot.application.documenttype.usecase;

import springboot.application.documenttype.dto.DocumentTypeResponse;
import springboot.application.documenttype.exception.DocumentTypeNotFoundApplicationException;
import springboot.domain.documenttype.model.valueobject.DocumentTypeId;
import springboot.domain.documenttype.port.repository.DocumentTypeRepository;

public class GetDocumentTypeByIdUseCase {
    private final DocumentTypeRepository repository;

    public GetDocumentTypeByIdUseCase(DocumentTypeRepository repository) {
        this.repository = repository;
    }

    public DocumentTypeResponse execute(DocumentTypeId id) {
        return repository.findById(id)
                .map(DocumentTypeResponse::from)
                .orElseThrow(() -> new DocumentTypeNotFoundApplicationException(id.value().toString()));
    }
}
