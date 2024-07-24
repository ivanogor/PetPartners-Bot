-- liquibase formatted sql
-- changeset n_mazin:step1_create_message_and_parents_tables
create TABLE messages
(
    id   SERIAL NOT NULL,
    text TEXT   NOT NULL,
    type TEXT   NOT NULL
);
insert into messages
values (1, 'Добро пожаловать в чат-бот для усыновления питомца', 'welcomeMessage');

create TABLE pet_parents
(
    id             SERIAL  NOT NULL,
    chat_id        BIGINT  NOT NULL,
    user_name      TEXT    NOT NULL,
    first_name     TEXT    NOT NULL,
    last_name      TEXT    NOT NULL,
    contacts       TEXT    NOT NULL,
    is_pets_parent BOOLEAN NOT NULL,
    need_report    BOOLEAN NOT NULL
);

-- changeset n_mazin:step2_drop_not_null_in_parents_table
ALTER TABLE pet_parents
    ALTER COLUMN first_name DROP NOT NULL;
ALTER TABLE pet_parents
    ALTER COLUMN last_name DROP NOT NULL;

-- changeset n_mazin:step3_drop_not_null_for_userName_in_parents_table
ALTER TABLE pet_parents
    ALTER COLUMN user_name DROP NOT NULL;

-- changeset n_mazin:step4_change_welcome_message_text
UPDATE messages
set text = 'Привет. Я чат-бот для взаимодействия с приютами для собачек. С моей помощью ты сожешь приютить собаку, а я подскажу как это сделать';

-- changeset ivanogor:step5_change_database_structure
drop table if exists users;
drop table if exists messages;

create table users
(
    chat_id        bigint primary key,
    contacts       text,
    is_pets_parent boolean
);

create table messages
(
    type text primary key,
    text text not null
);


insert into messages(type, text)
values ('welcomeMessage', 'Привет. Я чат-бот для взаимодействия с приютами для собачек.' ||
                          ' С моей помощью ты сожешь приютить собаку, а я подскажу как ' ||
                          'это сделать');

-- changeset n_mazin:step6_change_database
drop TABLE if exists pet_parents;
drop TABLE if exists users;

create TABLE users
(
    chat_id       BIGINT primary key,
    user_name     TEXT,
    contacts      TEXT,
    adoption_date TIMESTAMP
);

-- changeset Krockle:step1_add_table_animal_shelter
create TABLE animal_shelters(
    shelter_id BIGINT primary key,
    name TEXT,
    time_stamp TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE SEQUENCE public.animal_shelter_seq;
insert into animal_shelters values (nextval('animal_shelter_seq'),'Тестовый приют',DEFAULT);

-- changeset Krockle:step2_add_table_property_dict
create TABLE property_dict(
    prop_id BIGINT primary key,
    name TEXT,
    date_from timestamp without time zone NOT NULL DEFAULT NOW(),
    date_to timestamp without time zone
);
CREATE SEQUENCE property_dict_seq;

insert into property_dict(prop_id,name,date_from) values (nextval('property_dict_seq'),'О приюте',default);
insert into property_dict(prop_id,name,date_from) values (nextval('property_dict_seq'),'Расписание',default);
insert into property_dict(prop_id,name,date_from) values (nextval('property_dict_seq'),'Контакты охраны',default);
insert into property_dict(prop_id,name,date_from) values (nextval('property_dict_seq'),'Техника безопасности',default);
insert into property_dict(prop_id,name,date_from) values (nextval('property_dict_seq'),'Канал волонтеров',default);

-- changeset Krockle:step3_add_table_animal_shelters_props
create TABLE animal_shelters_props(
    animal_shelters_props_id BIGINT primary key,
    prop_id BIGINT,
    prop_val text,
    shelter_id BIGINT,
    date_from timestamp without time zone NOT NULL DEFAULT NOW(),
    date_to timestamp without time zone
);
CREATE SEQUENCE animal_shelters_props_seq;

-- changeset Krockle:step4_add_fk
ALTER TABLE public.animal_shelters_props
    ADD CONSTRAINT an_slt_props_to_props FOREIGN KEY (prop_id)
    REFERENCES public.property_dict (prop_id) MATCH SIMPLE;

ALTER TABLE public.animal_shelters_props
    ADD CONSTRAINT an_slt_props_to_an_slt FOREIGN KEY (shelter_id)
    REFERENCES public.animal_shelters (shelter_id) MATCH SIMPLE;

-- changeset Krockle:step5_add_params_to_props
insert into animal_shelters_props(animal_shelters_props_id, prop_id, prop_val, shelter_id, date_from)
values (nextval('animal_shelters_props_seq'),1,'Описание приюта',1,default);
insert into animal_shelters_props(animal_shelters_props_id, prop_id, prop_val, shelter_id, date_from)
values (nextval('animal_shelters_props_seq'),2,'Расписание',1,default);
insert into animal_shelters_props(animal_shelters_props_id, prop_id, prop_val, shelter_id, date_from)
values (nextval('animal_shelters_props_seq'),3,'Телефон и расписание охраны',1,default);
insert into animal_shelters_props(animal_shelters_props_id, prop_id, prop_val, shelter_id, date_from)
values (nextval('animal_shelters_props_seq'),4,'Описание ТБ',1,default);
insert into animal_shelters_props(animal_shelters_props_id, prop_id, prop_val, shelter_id, date_from)
values (nextval('animal_shelters_props_seq'),5,'-1002233623569',1,default);

-- changeset Krockle:step6_add_indexes
CREATE INDEX an_shlt_prop_p_id_s_id ON animal_shelters_props (prop_id,shelter_id);
-- changeset ivanogor:step7_change_welcome_message

update messages
set text = 'Привет! Я чат-бот для взаимодействия с приютами. С моей помощью ты сможешь приютить собаку или кошку, и я ' ||
           'подскажу, как это сделать.'
where type = 'welcomeMessage';
-- changeset ivanogor:step8_add_schedule_message_for_shelters
insert into messages(type, text)
values ('scheduleDogShelter', 'Расписание работы:
Приют открыт для посетителей с 9:00 до 18:00 с понедельника по субботу. Воскресенье — день отдыха.');

insert into messages(type, text)
values ('scheduleCatShelter', 'Расписание работы:
Приют открыт для посетителей с 10:00 до 17:00 с понедельника по субботу. Воскресенье — день отдыха.');

-- changeset ivanogor:step9_add_shelters_info
insert into messages(type, text)
values ('infoDogShelter', 'Собачий приют в Алматы — это место, где бездомные и брошенные собаки находят ' ||
                          'временное убежище и заботу, ожидая своего шанса найти новых, любящих хозяев.');

insert into messages(type, text)
values ('infoCatShelter', 'Кошачий приют в Алматы — это место, где каждая кошка находит заботу и внимание,' ||
                          ' ожидая своего шанса найти новый дом.');