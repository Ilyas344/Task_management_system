--liquibase formatted sql

--changeset Pro100:1
--2024-04-25--create-table-users
create table if not exists t_users
(
    id       bigserial primary key,
    username varchar(255) not null unique,
    email    varchar(255) not null unique,
    password varchar(255) not null
);

--2024-04-25--create-table-users_roles
create table if not exists t_users_roles
(
    user_id bigint       not null,
    role    varchar(255) not null,
    primary key (user_id, role),
    constraint fk_users_roles_users foreign key (user_id) references t_users (id) on delete cascade on update no action
);

