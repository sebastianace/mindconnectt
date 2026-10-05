package springboot.domain.mentalstatusexam.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.common.model.AggregateRoot;
import springboot.domain.common.validation.DomainGuard;
import springboot.domain.encounter.model.valueobject.EncounterId;
import springboot.domain.mentalstatusexam.event.MentalStatusExamRegisteredEvent;
import springboot.domain.mentalstatusexam.event.MentalStatusExamUpdatedEvent;
import springboot.domain.mentalstatusexam.model.valueobject.MentalStatusExamId;
import springboot.domain.professional.model.valueobject.ProfessionalId;

public class MentalStatusExam extends AggregateRoot {
    private final MentalStatusExamId id;
    private EncounterId encounterId;
    private String appearance;
    private String behavior;
    private String attitude;
    private String consciousness;
    private String orientation;
    private String attention;
    private String memory;
    private String speech;
    private String mood;
    private String affect;
    private String thoughtProcess;
    private String thoughtContent;
    private String perception;
    private String judgment;
    private String insight;
    private String psychomotorActivity;
    private String observations;
    private ProfessionalId createdBy;
    private final LocalDateTime createdAt;

    private MentalStatusExam(
            MentalStatusExamId id,
            EncounterId encounterId,
            String appearance,
            String behavior,
            String attitude,
            String consciousness,
            String orientation,
            String attention,
            String memory,
            String speech,
            String mood,
            String affect,
            String thoughtProcess,
            String thoughtContent,
            String perception,
            String judgment,
            String insight,
            String psychomotorActivity,
            String observations,
            ProfessionalId createdBy,
            LocalDateTime createdAt) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.encounterId = Objects.requireNonNull(encounterId, "encounterId must not be null");
        this.appearance = DomainGuard.requireText(appearance, "appearance");
        this.behavior = DomainGuard.requireText(behavior, "behavior");
        this.attitude = DomainGuard.requireText(attitude, "attitude");
        this.consciousness = DomainGuard.requireText(consciousness, "consciousness");
        this.orientation = DomainGuard.requireText(orientation, "orientation");
        this.attention = DomainGuard.requireText(attention, "attention");
        this.memory = DomainGuard.requireText(memory, "memory");
        this.speech = DomainGuard.requireText(speech, "speech");
        this.mood = DomainGuard.requireText(mood, "mood");
        this.affect = DomainGuard.requireText(affect, "affect");
        this.thoughtProcess = DomainGuard.requireText(thoughtProcess, "thoughtProcess");
        this.thoughtContent = DomainGuard.requireText(thoughtContent, "thoughtContent");
        this.perception = DomainGuard.requireText(perception, "perception");
        this.judgment = DomainGuard.requireText(judgment, "judgment");
        this.insight = DomainGuard.requireText(insight, "insight");
        this.psychomotorActivity = DomainGuard.requireText(psychomotorActivity, "psychomotorActivity");
        this.observations = DomainGuard.requireText(observations, "observations");
        this.createdBy = Objects.requireNonNull(createdBy, "createdBy must not be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
    }

    public static MentalStatusExam register(
            EncounterId encounterId,
            String appearance,
            String behavior,
            String attitude,
            String consciousness,
            String orientation,
            String attention,
            String memory,
            String speech,
            String mood,
            String affect,
            String thoughtProcess,
            String thoughtContent,
            String perception,
            String judgment,
            String insight,
            String psychomotorActivity,
            String observations,
            ProfessionalId createdBy) {
        MentalStatusExamId id = MentalStatusExamId.generate();
        LocalDateTime now = LocalDateTime.now();
        MentalStatusExam aggregate = new MentalStatusExam(
                id,
                encounterId,
                appearance,
                behavior,
                attitude,
                consciousness,
                orientation,
                attention,
                memory,
                speech,
                mood,
                affect,
                thoughtProcess,
                thoughtContent,
                perception,
                judgment,
                insight,
                psychomotorActivity,
                observations,
                createdBy,
                now);
        aggregate.recordEvent(new MentalStatusExamRegisteredEvent(id, now));
        return aggregate;
    }

    public static MentalStatusExam restore(
            MentalStatusExamId id,
            EncounterId encounterId,
            String appearance,
            String behavior,
            String attitude,
            String consciousness,
            String orientation,
            String attention,
            String memory,
            String speech,
            String mood,
            String affect,
            String thoughtProcess,
            String thoughtContent,
            String perception,
            String judgment,
            String insight,
            String psychomotorActivity,
            String observations,
            ProfessionalId createdBy,
            LocalDateTime createdAt) {
        return new MentalStatusExam(
                id,
                encounterId,
                appearance,
                behavior,
                attitude,
                consciousness,
                orientation,
                attention,
                memory,
                speech,
                mood,
                affect,
                thoughtProcess,
                thoughtContent,
                perception,
                judgment,
                insight,
                psychomotorActivity,
                observations,
                createdBy,
                createdAt);
    }

    public void update(
            EncounterId encounterId,
            String appearance,
            String behavior,
            String attitude,
            String consciousness,
            String orientation,
            String attention,
            String memory,
            String speech,
            String mood,
            String affect,
            String thoughtProcess,
            String thoughtContent,
            String perception,
            String judgment,
            String insight,
            String psychomotorActivity,
            String observations,
            ProfessionalId createdBy) {
        this.encounterId = Objects.requireNonNull(encounterId, "encounterId must not be null");
        this.appearance = DomainGuard.requireText(appearance, "appearance");
        this.behavior = DomainGuard.requireText(behavior, "behavior");
        this.attitude = DomainGuard.requireText(attitude, "attitude");
        this.consciousness = DomainGuard.requireText(consciousness, "consciousness");
        this.orientation = DomainGuard.requireText(orientation, "orientation");
        this.attention = DomainGuard.requireText(attention, "attention");
        this.memory = DomainGuard.requireText(memory, "memory");
        this.speech = DomainGuard.requireText(speech, "speech");
        this.mood = DomainGuard.requireText(mood, "mood");
        this.affect = DomainGuard.requireText(affect, "affect");
        this.thoughtProcess = DomainGuard.requireText(thoughtProcess, "thoughtProcess");
        this.thoughtContent = DomainGuard.requireText(thoughtContent, "thoughtContent");
        this.perception = DomainGuard.requireText(perception, "perception");
        this.judgment = DomainGuard.requireText(judgment, "judgment");
        this.insight = DomainGuard.requireText(insight, "insight");
        this.psychomotorActivity = DomainGuard.requireText(psychomotorActivity, "psychomotorActivity");
        this.observations = DomainGuard.requireText(observations, "observations");
        this.createdBy = Objects.requireNonNull(createdBy, "createdBy must not be null");
        LocalDateTime occurredOn = LocalDateTime.now();
        recordEvent(new MentalStatusExamUpdatedEvent(
                        this.id,
                        this.encounterId,
                        this.appearance,
                        this.behavior,
                        this.attitude,
                        this.consciousness,
                        this.orientation,
                        this.attention,
                        this.memory,
                        this.speech,
                        this.mood,
                        this.affect,
                        this.thoughtProcess,
                        this.thoughtContent,
                        this.perception,
                        this.judgment,
                        this.insight,
                        this.psychomotorActivity,
                        this.observations,
                        this.createdBy,
                        occurredOn));
    }

    public MentalStatusExamId id() {
        return id;
    }

    public EncounterId encounterId() {
        return encounterId;
    }

    public String appearance() {
        return appearance;
    }

    public String behavior() {
        return behavior;
    }

    public String attitude() {
        return attitude;
    }

    public String consciousness() {
        return consciousness;
    }

    public String orientation() {
        return orientation;
    }

    public String attention() {
        return attention;
    }

    public String memory() {
        return memory;
    }

    public String speech() {
        return speech;
    }

    public String mood() {
        return mood;
    }

    public String affect() {
        return affect;
    }

    public String thoughtProcess() {
        return thoughtProcess;
    }

    public String thoughtContent() {
        return thoughtContent;
    }

    public String perception() {
        return perception;
    }

    public String judgment() {
        return judgment;
    }

    public String insight() {
        return insight;
    }

    public String psychomotorActivity() {
        return psychomotorActivity;
    }

    public String observations() {
        return observations;
    }

    public ProfessionalId createdBy() {
        return createdBy;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }
}
