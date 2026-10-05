package springboot.application.contact.usecase;

import springboot.application.contact.dto.ContactResponse;
import springboot.application.contact.exception.ContactNotFoundApplicationException;
import springboot.domain.contact.model.valueobject.ContactId;
import springboot.domain.contact.port.repository.ContactRepository;

public class GetContactByIdUseCase {
    private final ContactRepository repository;

    public GetContactByIdUseCase(ContactRepository repository) {
        this.repository = repository;
    }

    public ContactResponse execute(ContactId id) {
        return repository.findById(id)
                .map(ContactResponse::from)
                .orElseThrow(() -> new ContactNotFoundApplicationException(id.value().toString()));
    }
}
