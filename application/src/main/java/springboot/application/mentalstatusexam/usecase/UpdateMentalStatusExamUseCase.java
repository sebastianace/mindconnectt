package springboot.application.mentalstatusexam.usecase;

import springboot.application.common.exception.ReferenceNotFoundApplicationException;
import springboot.application.mentalstatusexam.command.UpdateMentalStatusExamCommand;
import springboot.application.mentalstatusexam.dto.MentalStatusExamResponse;
import springboot.application.mentalstatusexam.exception.MentalStatusExamNotFoundApplicationException;
import springboot.domain.common.port.DomainEventPublisher;
import springboot.domain.encounter.port.repository.EncounterRepository;
import springboot.domain.mentalstatusexam.model.aggregate.MentalStatusExam;
import springboot.domain.mentalstatusexam.port.repository.MentalStatusExamRepository;
import springboot.domain.professional.port.repository.ProfessionalRepository;

public class UpdateMentalStatusExamUseCase {
    private final MentalStatusExamRepository repository;
    private final EncounterRepository encounterRepository;
    private final ProfessionalRepository professionalRepository;
    private final DomainEventPublisher eventPublisher;

    public UpdateMentalStatusExamUseCase(
            MentalStatusExamRepository repository,
            EncounterRepository encounterRepository,
            ProfessionalRepository professionalRepository,
            DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.encounterRepository = encounterRepository;
        this.professionalRepository = professionalRepository;
        this.eventPublisher = eventPublisher;
    }

    public MentalStatusExamResponse execute(UpdateMentalStatusExamCommand command) {
        MentalStatusExam aggregate = repository.findById(command.id())
                .orElseThrow(() -> new MentalStatusExamNotFoundApplicationException(command.id().value().toString()));
        validateReferences(command);
        aggregate.update(
                command.encounterId(),
                command.appearance(),
                command.behavior(),
                command.attitude(),
                command.consciousness(),
                command.orientation(),
                command.attention(),
                command.memory(),
                command.speech(),
                command.mood(),
                command.affect(),
                command.thoughtProcess(),
                command.thoughtContent(),
                command.perception(),
                command.judgment(),
                command.insight(),
                command.psychomotorActivity(),
                command.observations(),
                command.createdBy());
        MentalStatusExam saved = repository.save(aggregate);
        eventPublisher.publish(aggregate.domainEvents());
        aggregate.clearDomainEvents();
        return MentalStatusExamResponse.from(saved);
    }

    private void validateReferences(UpdateMentalStatusExamCommand command) {
        if (!encounterRepository.existsById(command.encounterId())) {
            throw new ReferenceNotFoundApplicationException("Encounter", command.encounterId().value());
        }
        if (!professionalRepository.existsById(command.createdBy())) {
            throw new ReferenceNotFoundApplicationException("Professional", command.createdBy().value());
        }
    }
}
