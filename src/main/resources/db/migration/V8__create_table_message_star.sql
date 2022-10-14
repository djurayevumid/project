CREATE SEQUENCE message_star_seq START WITH 1;

Create Table message_stars
(
    id          bigint primary key not null,
    user_id     bigint,
    message_id bigint,
    persist_date timestamp,
    FOREIGN KEY (user_id) REFERENCES user_entity (id),
    FOREIGN KEY (message_id) REFERENCES message (id)
);
