INSERT INTO m_users (id, username, email)
VALUES (1, 'Ivan', 'ivan@gmail.com'),
       (2, 'Sergey', 'sergey@email.com'),
       (3, 'Vega', 'vera@gmail.com'),
       (4, 'Evgeniy', 'evgeniy@email.com'),
       (5, 'Maxim', 'maxim@email.com'),
       (6, 'Aleksey', 'aleksey@email.com'),
       (7, 'Andrey', 'andrey@email.com'),
       (8, 'Anton', 'anton@email.com'),
       (9, 'Lena', 'lena@email.com'),
       (10, 'Egor1', 'egor1@email.com'),
       (11, 'Ivan1', 'ivan1@gmail.com'),
       (12, 'Sergey1', 'sergey1@email.com'),
       (13, 'Vega1', 'vera1@gmail.com'),
       (14, 'Evgeniy1', 'evgeniy1@email.com'),
       (15, 'Maxim1', 'maxim1@email.com'),
       (16, 'Aleksey1', 'aleksey1@email.com'),
       (17, 'Andrey1', 'andrey1@email.com'),
       (18, 'Anton1', 'anton1@email.com'),
       (19, 'Lena1', 'lena1@email.com'),
       (20, 'Egor1', 'egor1@email.com'),
       (21, 'Ivan2', 'ivan2@gmail.com'),
       (22, 'Sergey2', 'sergey2@email.com'),
       (23, 'Vega2', 'vera2@gmail.com'),
       (24, 'Evgeniy2', 'evgeniy2@email.com'),
       (25, 'Maxim2', 'maxim2@email.com'),
       (26, 'Aleksey2', 'aleksey2@email.com'),
       (27, 'Andrey2', 'andrey2@email.com'),
       (28, 'Anton2', 'anton2@email.com'),
       (29, 'Lena2', 'lena2@email.com'),
       (30, 'Egor2', 'egor2@email.com'),
       (31, 'Ivan3', 'ivan3@gmail.com'),
       (32, 'Sergey3', 'sergey3@email.com'),
       (33, 'Vega3', 'vera3@gmail.com'),
       (34, 'Evgeniy3', 'evgeniy3@email.com'),
       (35, 'Maxim3', 'maxim3@email.com'),
       (36, 'Aleksey3', 'aleksey3@email.com'),
       (37, 'Andrey3', 'andrey3@email.com'),
       (38, 'Anton3', 'anton3@email.com'),
       (39, 'Lena3', 'lena3@email.com'),
       (40, 'Egor3', 'egor3@email.com'),
       (41, 'Ivan4', 'ivan4@gmail.com'),
       (42, 'Sergey4', 'sergey4@email.com'),
       (43, 'Vega4', 'vera4@gmail.com'),
       (44, 'Evgeniy4', 'evgeniy4@email.com'),
       (45, 'Maxim4', 'maxim4@email.com'),
       (46, 'Aleksey4', 'aleksey4@email.com'),
       (47, 'Andrey4', 'andrey4@email.com'),
       (48, 'Anton4', 'anton4@email.com'),
       (49, 'Lena4', 'lena4@email.com'),
       (50, 'Egor4', 'egor4@email.com');

INSERT INTO m_roles (user_id, role_name)
values (1, 'USER'),
       (2, 'USER'),
       (3, 'USER'),
       (4, 'USER'),
       (5, 'USER'),
       (6, 'USER'),
       (7, 'USER'),
       (8, 'USER'),
       (9, 'USER'),
       (10, 'USER'),
       (11, 'USER'),
       (12, 'USER'),
       (13, 'USER'),
       (14, 'USER'),
       (15, 'USER'),
       (16, 'USER'),
       (17, 'USER'),
       (18, 'USER'),
       (19, 'USER'),
       (20, 'USER'),
       (21, 'USER'),
       (22, 'USER'),
       (23, 'USER'),
       (24, 'USER'),
       (25, 'USER'),
       (26, 'USER'),
       (27, 'USER'),
       (28, 'USER'),
       (29, 'USER'),
       (30, 'USER'),
       (41, 'USER'),
       (42, 'USER'),
       (43, 'USER'),
       (44, 'USER'),
       (45, 'USER'),
       (46, 'USER'),
       (47, 'USER'),
       (48, 'USER'),
       (49, 'USER'),
       (50, 'USER'),
       (1, 'ADMIN'),
       (2, 'ADMIN'),
       (3, 'ADMIN'),
       (4, 'ADMIN'),
       (5, 'ADMIN'),
       (6, 'ADMIN'),
       (7, 'ADMIN'),
       (8, 'ADMIN'),
       (9, 'ADMIN'),
       (10, 'ADMIN');

INSERT INTO m_message (id, text, user_id)
values (1, 'kek', 1),
       (2, 'lol', 2),
       (3, 'kek lol', 3);

INSERT INTO m_goods (id, good_name)
VALUES (1, 'milk'),
       (2, 'cola'),
       (3, 'meat'),
       (4, 'juice'),
       (5, 'apple'),
       (6, 'bear'),
       (7, 'cheese'),
       (8, 'sauce'),
       (9, 'biscuits'),
       (10, 'sausage'),
       (11, 'ice cream'),
       (12, 'fish'),
       (13, 'pizza'),
       (14, 'water'),
       (15, 'chocolate'),
       (16, 'potato'),
       (17, 'tomato'),
       (18, 'onion'),
       (19, 'garlic'),
       (20, 'Strawberry');

INSERT INTO l_user_goods (good_id, user_id)
VALUES (1, 1),
       (1, 2),
       (7, 3),
       (9, 2),
       (11, 2),
       (12, 3),
       (20, 2),
       (1, 3),
       (12, 2),
       (1, 18),
       (14, 12),
       (16, 27),
       (15, 25),
       (1, 12),
       (1, 13),
       (7, 8),
       (10, 9),
       (5, 40),
       (5, 3),
       (5, 2),
       (6, 7),
       (10, 14),
       (3, 4),
       (3, 5),
       (5, 5);