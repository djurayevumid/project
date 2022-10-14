insert into role(id, name) VALUES
(1, 'ROLE_USER');

insert into user_entity(id, password, role_id, email, is_enabled, is_deleted) values
(100, '$2a$12$CZELphIIfCZsAnc7JDdW.eK9k1bRNmkIvwtJVp8B.DAj7HUScf5D.', 1, 'user@mail.ru', true, false);

insert into question(id, title, description, user_id)
values
    (100, 'any', 'any', 100);



