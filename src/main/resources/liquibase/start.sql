-- changeset liquibase:1
create table users (
    id UUID NOT NULL,
    login VARCHAR(100),
    password VARCHAR(255),
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    patronymic VARCHAR(100),
    birthday DATE,
    created DATE,
    updated DATE,
    is_deleted BOOLEAN,
    CONSTRAINT pk_users PRIMARY KEY(id)
    );
-- changeset liquibase:2
CREATE TABLE roles
(
    id        UUID NOT NULL,
    created   date,
    updated   date,
    is_deleted BOOLEAN,
    name      VARCHAR(255),
    CONSTRAINT pk_roles PRIMARY KEY (id)
);
-- changeset liquibase:3
CREATE TABLE user_roles
(
    user_id UUID NOT NULL,
    role_id UUID NOT NULL
);
-- changeset liquibase:4
ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE SET NULL ;
-- changeset liquibase:5
ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;
INSERT INTO roles (id, created, updated, is_deleted, name)
VALUES ('67042b9d-cfee-4869-ac53-fe683fe28c64', now(), null, false, 'ROLE_USER'),
       ('73de2a0c-13b3-441d-86ea-81bb0478237c', now(), null, false, 'ROLE_ADMIN')