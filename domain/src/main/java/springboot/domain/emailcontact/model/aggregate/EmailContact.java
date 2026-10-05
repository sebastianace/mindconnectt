package springboot.domain.emailcontact.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.common.model.AggregateRoot;
import springboot.domain.common.validation.DomainGuard;
import springboot.domain.contact.model.valueobject.ContactId;
import springboot.domain.emailcontact.event.EmailContactRegisteredEvent;
import springboot.domain.emailcontact.event.EmailContactUpdatedEvent;
import springboot.domain.emailcontact.model.valueobject.EmailContactId;

public class EmailContact extends AggregateRoot {
    private final EmailContactId id;
    private ContactId contactId;
    private String email;
    private String notes;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private EmailContact(
            EmailContactId id,
            ContactId contactId,
            String email,
            String notes,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.contactId = Objects.requireNonNull(contactId, "contactId must not be null");
        this.email = DomainGuard.requireEmail(email, "email");
        this.notes = DomainGuard.requireText(notes, "notes");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public static EmailContact register(
            ContactId contactId,
            String email,
            String notes) {
        EmailContactId id = EmailContactId.generate();
        LocalDateTime now = LocalDateTime.now();
        EmailContact aggregate = new EmailContact(
                id,
                contactId,
                email,
                notes,
                now,
                now);
        aggregate.recordEvent(new EmailContactRegisteredEvent(id, now));
        return aggregate;
    }

    public static EmailContact restore(
            EmailContactId id,
            ContactId contactId,
            String email,
            String notes,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        return new EmailContact(
                id,
                contactId,
                email,
                notes,
                createdAt,
                updatedAt);
    }

    public void update(
            ContactId contactId,
            String email,
            String notes) {
        this.contactId = Objects.requireNonNull(contactId, "contactId must not be null");
        this.email = DomainGuard.requireEmail(email, "email");
        this.notes = DomainGuard.requireText(notes, "notes");
        this.updatedAt = LocalDateTime.now();
        recordEvent(new EmailContactUpdatedEvent(
                        this.id,
                        this.contactId,
                        this.email,
                        this.notes,
                        this.updatedAt));
    }

    public EmailContactId id() {
        return id;
    }

    public ContactId contactId() {
        return contactId;
    }

    public String email() {
        return email;
    }

    public String notes() {
        return notes;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }
}
