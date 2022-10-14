CREATE SEQUENCE bookmarks_seq START WITH 1;

ALTER TABLE bookmarks ADD COLUMN persist_date timestamp;