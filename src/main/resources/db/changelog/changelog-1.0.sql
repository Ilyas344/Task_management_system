--liquibase formatted sql

--changeset Pro100:1
--2025-02-04--create-table-users
create table if not exists users
(
    id       bigserial primary key,
    username varchar(255) not null unique,
    email    varchar(255) not null unique,
    password varchar(255) not null
);

--2025-02-04--create-table-roles
CREATE TABLE if not exists roles
(
    id   bigserial primary key,
    name VARCHAR(20) unique not null
);
--2025-02-04--create-table-user_roles
CREATE TABLE if not exists user_roles (
                            user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
                            role_id BIGINT REFERENCES roles(id) ON DELETE CASCADE,
                            PRIMARY KEY (user_id, role_id)
);

-- Создание таблицы tasks
CREATE TABLE if not exists tasks (
                       id BIGSERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       description VARCHAR(1024),
                       status VARCHAR(20) NOT NULL,
                       priority VARCHAR(20) NOT NULL,
                       author_id BIGINT NOT NULL,
                       assignee VARCHAR(255),
                       FOREIGN KEY (author_id) REFERENCES users(id)
);

-- Создание таблицы task_comments для хранения комментариев
CREATE TABLE if not exists task_comments (
                               task_id BIGINT NOT NULL,
                               comments VARCHAR(1024) NOT NULL,
                               PRIMARY KEY (task_id, comments),
                               FOREIGN KEY (task_id) REFERENCES tasks(id)
);


CREATE SEQUENCE IF NOT EXISTS users_id_seq;

alter sequence users_id_seq owner to postgres;

alter sequence users_id_seq owned by users.id;

CREATE SEQUENCE IF NOT EXISTS roles_id_seq;

alter sequence roles_id_seq owner to postgres;

alter sequence roles_id_seq owned by roles.id;

CREATE SEQUENCE IF NOT EXISTS tasks_id_seq;

alter sequence tasks_id_seq owner to postgres;

alter sequence tasks_id_seq owned by tasks.id;

CREATE SEQUENCE IF NOT EXISTS roles_seq
    increment by 50;

alter sequence roles_seq owner to postgres;

CREATE SEQUENCE IF NOT EXISTS tasks_seq
    increment by 50;

alter sequence tasks_seq owner to postgres;

CREATE SEQUENCE IF NOT EXISTS users_seq
    increment by 50;

alter sequence users_seq owner to postgres;
