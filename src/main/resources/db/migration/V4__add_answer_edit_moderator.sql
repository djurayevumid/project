ALTER TABLE answer ADD COLUMN edit_moderator BIGINT REFERENCES user_entity (id);
