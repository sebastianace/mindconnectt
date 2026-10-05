package springboot.application.phonecontact.usecase;

import springboot.application.phonecontact.dto.PhoneContactResponse;
import springboot.application.phonecontact.exception.PhoneContactNotFoundApplicationException;
import springboot.domain.phonecontact.model.valueobject.PhoneContactId;
import springboot.domain.phonecontact.port.repository.PhoneContactRepository;

public class GetPhoneContactByIdUseCase {
    private final PhoneContactRepository repository;

    public GetPhoneContactByIdUseCase(PhoneContactRepository repository) {
        this.repository = repository;
    }

    public PhoneContactResponse execute(PhoneContactId id) {
        return repository.findById(id)
                .map(PhoneContactResponse::from)
                .orElseThrow(() -> new PhoneContactNotFoundApplicationException(id.value().toString()));
    }
}
