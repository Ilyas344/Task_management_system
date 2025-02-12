

insert into users (username, email, password)
values ('user1', 'user1@example.com', '$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W'),
       ('user2', 'user2@example.com', '$2a$10$fFLij9aYgaNCFPTL9WcA/uoCRukxnwf.vOQ8nrEEOskrCNmGsxY7m');

INSERT INTO roles (name)
VALUES
    ('ROLE_ADMIN'),
    ('ROLE_USER'),
    ('ROLE_MODERATOR');

-- Получаем ID ролей и вставляем в user_roles
insert into user_roles (user_id, role_id)
select 1, id from roles where name = 'ROLE_USER'; -- user1 - ROLE_USER
insert into user_roles (user_id, role_id)
select 1, id from roles where name = 'ROLE_ADMIN'; -- user1 - ROLE_ADMIN
insert into user_roles (user_id, role_id)
select 2, id from roles where name = 'ROLE_MODERATOR'; -- user2 - ROLE_MODERATOR