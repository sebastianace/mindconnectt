CREATE TABLE users (
    id CHAR(36) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    is_enabled BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uk_users_username UNIQUE (username)
) ENGINE = InnoDB;
