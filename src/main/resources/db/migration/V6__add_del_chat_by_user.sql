ALTER TABLE single_chat
ADD COLUMN del_by_user_two BOOL NOT NULL DEFAULT FALSE,
ADD COLUMN del_by_user_one BOOL NOT NULL DEFAULT FALSE;

ALTER TABLE single_chat RENAME COLUMN use_two_id TO user_two_id;
