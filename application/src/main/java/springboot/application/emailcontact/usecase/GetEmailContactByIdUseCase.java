package springboot.application.emailcontact.usecase;

import springboot.application.emailcontact.dto.EmailContactResponse;
import springboot.application.emailcontact.exception.EmailContactNotFoundApplicationException;
import springboot.domain.emailcontact.model.valueobject.EmailContactId;
import springboot.domain.emailcontact.port.repository.EmailContactRepository;

public class GetEmailContactByIdUseCase {
    private final EmailContactRepository repository;

    public GetEmailContactByIdUseCase(EmailContactRepository repository) {
        this.repository = repository;
    }

    public EmailContactResponse execute(EmailContactId id) {
        return repository.findById(id)
                .map(EmailContactResponse::from)
                .orElseThrow(() -> new EmailContactNotFoundApplicationException(id.value().toString()));
    }
}
