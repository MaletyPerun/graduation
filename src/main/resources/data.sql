INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@yandex.ru', '{noop}user'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (NAME, ADDRESS)
VALUES  ('McDonalds', 'Пушкинская 8'),
        ('Все хорошо', 'Ломоносовская 17'),
        ('Доски', 'Московский 76');

INSERT INTO MEAL (price, description, restaurant_id)
VALUES ('500', 'Завтрак', 1),
       ('600', 'Обед', 2),
       ('700', 'Ужин', 3),
       ('800', 'Завтрак', 2),
       ('900', 'Обед', 3),
       ('400', 'Ужин', 1);