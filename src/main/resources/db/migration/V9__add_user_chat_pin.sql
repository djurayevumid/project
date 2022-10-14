CREATE SEQUENCE user_chat_pin_seq START WITH 1;

Create Table user_chat_pins
(
    id          bigint primary key not null,
    user_id     bigint,
    chat_id bigint,
    persist_date timestamp,
    FOREIGN KEY (user_id) REFERENCES user_entity (id),
    FOREIGN KEY (chat_id) REFERENCES chat (id)
);
