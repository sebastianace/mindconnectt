package springboot.application.emailcontact.usecase;

import java.util.List;

import springboot.application.emailcontact.dto.EmailContactResponse;
import springboot.domain.emailcontact.port.repository.EmailContactRepository;

public class ListEmailContactUseCase {
    private final EmailContactRepository repository;

    public ListEmailContactUseCase(EmailContactRepository repository) {
        this.repository = repository;
    }

    public List<EmailContactResponse> execute() {
        return repository.findAll().stream()
                .map(EmailContactResponse::from)
                .toList();
    }
}
