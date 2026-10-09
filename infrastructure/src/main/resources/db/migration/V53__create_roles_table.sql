CREATE TABLE roles (
    id CHAR(36) NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id),
    CONSTRAINT uk_roles_name UNIQUE (name),
    CONSTRAINT ck_roles_name_prefix CHECK (LEFT(name, 5) = 'ROLE_')
) ENGINE = InnoDB;
