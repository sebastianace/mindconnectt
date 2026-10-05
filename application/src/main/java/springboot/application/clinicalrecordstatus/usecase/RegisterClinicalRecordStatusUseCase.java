package springboot.application.clinicalrecordstatus.usecase;

import springboot.application.clinicalrecordstatus.command.RegisterClinicalRecordStatusCommand;
import springboot.application.clinicalrecordstatus.dto.ClinicalRecordStatusResponse;
import springboot.application.common.exception.DuplicateResourceApplicationException;
import springboot.domain.clinicalrecordstatus.model.aggregate.ClinicalRecordStatus;
import springboot.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;
import springboot.domain.common.port.DomainEventPublisher;

public class RegisterClinicalRecordStatusUseCase {
    private final ClinicalRecordStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    public RegisterClinicalRecordStatusUseCase(
            ClinicalRecordStatusRepository repository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public ClinicalRecordStatusResponse execute(RegisterClinicalRecordStatusCommand command) {
        if (repository.existsByCode(command.code())) {
            throw new DuplicateResourceApplicationException("ClinicalRecordStatus", "code", command.code());
        }
        ClinicalRecordStatus aggregate = ClinicalRecordStatus.register(
                command.code(),
                command.name());
        ClinicalRecordStatus saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return ClinicalRecordStatusResponse.from(saved);
    }
}
