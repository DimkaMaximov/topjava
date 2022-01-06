DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE global_meals_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);



INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2021-12-19 10:23:54','Завтрак', '800', 100000 ),
        ('2021-12-19 11:23:54','Обед', '900', 100000 ),
        ('2021-12-19 12:23:54','Ужин', '1000', 100000 );

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2021-12-20 10:23:54','Завтрак', '800', 100001 ),
       ('2021-12-29 11:23:54','Обед', '900', 100001 ),
       ('2021-12-20 12:23:54','Ужин', '1000', 100001 );

INSERT INTO meals (description, calories, user_id)
VALUES ('Новый завтрак', '500', 100001);
