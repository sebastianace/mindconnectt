package springboot.domain.patient.model.aggregate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import springboot.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import springboot.domain.common.model.AggregateRoot;
import springboot.domain.common.validation.DomainGuard;
import springboot.domain.documenttype.model.valueobject.DocumentTypeId;
import springboot.domain.gender.model.valueobject.GenderId;
import springboot.domain.patient.event.PatientRegisteredEvent;
import springboot.domain.patient.event.PatientUpdatedEvent;
import springboot.domain.patient.model.valueobject.PatientId;
import springboot.domain.professional.model.valueobject.ProfessionalId;

public class Patient extends AggregateRoot {
    private final PatientId id;
    private DocumentTypeId documentTypeId;
    private String documentNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private String secondLastName;
    private LocalDate birthDate;
    private GenderId biologicalSexId;
    private GenderId genderIdentityId;
    private String email;
    private String phone;
    private String address;
    private boolean active;
    private ProfessionalId createdBy;
    private ProfessionalId updatedBy;
    private CityMunicipalityId cityId;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Patient(
            PatientId id,
            DocumentTypeId documentTypeId,
            String documentNumber,
            String firstName,
            String middleName,
            String lastName,
            String secondLastName,
            LocalDate birthDate,
            GenderId biologicalSexId,
            GenderId genderIdentityId,
            String email,
            String phone,
            String address,
            boolean active,
            ProfessionalId createdBy,
            ProfessionalId updatedBy,
            CityMunicipalityId cityId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.documentTypeId = Objects.requireNonNull(documentTypeId, "documentTypeId must not be null");
        this.documentNumber = DomainGuard.requireText(documentNumber, "documentNumber");
        this.firstName = DomainGuard.requireText(firstName, "firstName");
        this.middleName = middleName;
        this.lastName = DomainGuard.requireText(lastName, "lastName");
        this.secondLastName = secondLastName;
        this.birthDate = Objects.requireNonNull(birthDate, "birthDate must not be null");
        this.biologicalSexId = Objects.requireNonNull(biologicalSexId, "biologicalSexId must not be null");
        this.genderIdentityId = Objects.requireNonNull(genderIdentityId, "genderIdentityId must not be null");
        this.email = DomainGuard.requireEmail(email, "email");
        this.phone = DomainGuard.requireText(phone, "phone");
        this.address = DomainGuard.requireText(address, "address");
        this.active = active;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.cityId = Objects.requireNonNull(cityId, "cityId must not be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
        validateInvariants();
    }

    public static Patient register(
            DocumentTypeId documentTypeId,
            String documentNumber,
            String firstName,
            String middleName,
            String lastName,
            String secondLastName,
            LocalDate birthDate,
            GenderId biologicalSexId,
            GenderId genderIdentityId,
            String email,
            String phone,
            String address,
            boolean active,
            ProfessionalId createdBy,
            ProfessionalId updatedBy,
            CityMunicipalityId cityId) {
        PatientId id = PatientId.generate();
        LocalDateTime now = LocalDateTime.now();
        Patient aggregate = new Patient(
                id,
                documentTypeId,
                documentNumber,
                firstName,
                middleName,
                lastName,
                secondLastName,
                birthDate,
                biologicalSexId,
                genderIdentityId,
                email,
                phone,
                address,
                active,
                createdBy,
                updatedBy,
                cityId,
                now,
                now);
        aggregate.recordEvent(new PatientRegisteredEvent(id, now));
        return aggregate;
    }

    public static Patient restore(
            PatientId id,
            DocumentTypeId documentTypeId,
            String documentNumber,
            String firstName,
            String middleName,
            String lastName,
            String secondLastName,
            LocalDate birthDate,
            GenderId biologicalSexId,
            GenderId genderIdentityId,
            String email,
            String phone,
            String address,
            boolean active,
            ProfessionalId createdBy,
            ProfessionalId updatedBy,
            CityMunicipalityId cityId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        return new Patient(
                id,
                documentTypeId,
                documentNumber,
                firstName,
                middleName,
                lastName,
                secondLastName,
                birthDate,
                biologicalSexId,
                genderIdentityId,
                email,
                phone,
                address,
                active,
                createdBy,
                updatedBy,
                cityId,
                createdAt,
                updatedAt);
    }

    public void update(
            DocumentTypeId documentTypeId,
            String documentNumber,
            String firstName,
            String middleName,
            String lastName,
            String secondLastName,
            LocalDate birthDate,
            GenderId biologicalSexId,
            GenderId genderIdentityId,
            String email,
            String phone,
            String address,
            boolean active,
            ProfessionalId createdBy,
            ProfessionalId updatedBy,
            CityMunicipalityId cityId) {
        this.documentTypeId = Objects.requireNonNull(documentTypeId, "documentTypeId must not be null");
        this.documentNumber = DomainGuard.requireText(documentNumber, "documentNumber");
        this.firstName = DomainGuard.requireText(firstName, "firstName");
        this.middleName = middleName;
        this.lastName = DomainGuard.requireText(lastName, "lastName");
        this.secondLastName = secondLastName;
        this.birthDate = Objects.requireNonNull(birthDate, "birthDate must not be null");
        this.biologicalSexId = Objects.requireNonNull(biologicalSexId, "biologicalSexId must not be null");
        this.genderIdentityId = Objects.requireNonNull(genderIdentityId, "genderIdentityId must not be null");
        this.email = DomainGuard.requireEmail(email, "email");
        this.phone = DomainGuard.requireText(phone, "phone");
        this.address = DomainGuard.requireText(address, "address");
        this.active = active;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.cityId = Objects.requireNonNull(cityId, "cityId must not be null");
        validateInvariants();
        this.updatedAt = LocalDateTime.now();
        recordEvent(new PatientUpdatedEvent(
                        this.id,
                        this.documentTypeId,
                        this.documentNumber,
                        this.firstName,
                        this.middleName,
                        this.lastName,
                        this.secondLastName,
                        this.birthDate,
                        this.biologicalSexId,
                        this.genderIdentityId,
                        this.email,
                        this.phone,
                        this.address,
                        this.active,
                        this.createdBy,
                        this.updatedBy,
                        this.cityId,
                        this.updatedAt));
    }


    private void validateInvariants() {
        DomainGuard.require(!birthDate.isAfter(LocalDate.now()), "birthDate must not be in the future");
    }

    public PatientId id() {
        return id;
    }

    public DocumentTypeId documentTypeId() {
        return documentTypeId;
    }

    public String documentNumber() {
        return documentNumber;
    }

    public String firstName() {
        return firstName;
    }

    public String middleName() {
        return middleName;
    }

    public String lastName() {
        return lastName;
    }

    public String secondLastName() {
        return secondLastName;
    }

    public LocalDate birthDate() {
        return birthDate;
    }

    public GenderId biologicalSexId() {
        return biologicalSexId;
    }

    public GenderId genderIdentityId() {
        return genderIdentityId;
    }

    public String email() {
        return email;
    }

    public String phone() {
        return phone;
    }

    public String address() {
        return address;
    }

    public boolean active() {
        return active;
    }

    public ProfessionalId createdBy() {
        return createdBy;
    }

    public ProfessionalId updatedBy() {
        return updatedBy;
    }

    public CityMunicipalityId cityId() {
        return cityId;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }
}
