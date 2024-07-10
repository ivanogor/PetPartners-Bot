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