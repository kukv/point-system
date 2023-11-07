\connect wagokoro
set role bradmin;
set search_path to point;

insert into transaction_type(type)
VALUES ('利用'),
       ('獲得'),
       ('キャンセル'),
       ('失効');