-- liquibase formatted sql
-- changeset n_mazin:step1_create_message_and_parents_tables
create TABLE messages(
    id SERIAL NOT NULL,
    text TEXT NOT NULL,
    type TEXT NOT NULL
);
insert into messages values (1,'Добро пожаловать в чат-бот для усыновления питомца', 'welcomeMessage');

create TABLE pet_parents(
    id SERIAL NOT NULL,
    chat_id BIGINT NOT NULL,
    user_name TEXT NOT NULL,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    contacts TEXT NOT NULL,
    is_pets_parent BOOLEAN NOT NULL,
    need_report BOOLEAN NOT NULL
);

-- changeset n_mazin:step2_drop_not_null_in_parents_table
ALTER TABLE pet_parents ALTER COLUMN first_name DROP NOT NULL;
ALTER TABLE pet_parents ALTER COLUMN last_name DROP NOT NULL;

-- changeset n_mazin:step3_drop_not_null_for_userName_in_parents_table
ALTER TABLE pet_parents ALTER COLUMN user_name DROP NOT NULL;

-- changeset n_mazin:step4_change_welcome_message_text
UPDATE messages set text = 'Привет. Я чат-бот для взаимодействия с приютами для собачек. С моей помощью ты сожешь приютить собаку, а я подскажу как это сделать';

-- changeset ivanogor: change_database_structure
drop table if exists users;
drop table if exists messages;


create table users(
    chat_id bigint primary key,
    contacts text,
    is_pets_parent boolean
);

create table messages(
    type text primary key,
    text text null
);


insert into messages(type, text) values ('welcomeMessage', 'Привет. Я чат-бот для взаимодействия с приютами для собачек.' ||
                                                           ' С моей помощью ты сожешь приютить собаку, а я подскажу как ' ||
                                                           'это сделать');
