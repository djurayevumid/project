CREATE SEQUENCE answer_seq START WITH 1;
CREATE SEQUENCE answer_vote_seq START WITH 1;
CREATE SEQUENCE badge_seq START WITH 1;
CREATE SEQUENCE chat_seq START WITH 1;
CREATE SEQUENCE comment_seq START WITH 1;
CREATE SEQUENCE ignore_tag_seq START WITH 1;
CREATE SEQUENCE message_seq START WITH 1;
CREATE SEQUENCE question_seq START WITH 1;
CREATE SEQUENCE question_viewed_seq START WITH 1;
CREATE SEQUENCE related_tag_seq START WITH 1;
CREATE SEQUENCE reputation_seq START WITH 1;
CREATE SEQUENCE role_seq START WITH 1;
CREATE SEQUENCE tag_seq START WITH 1;
CREATE SEQUENCE tracked_tag_seq START WITH 1;
CREATE SEQUENCE user_seq START WITH 1;
CREATE SEQUENCE user_badges_seq START WITH 1;
CREATE SEQUENCE user_favorite_question_seq START WITH 1;
CREATE SEQUENCE vote_question_seq START WITH 1;

Create Table role (
 id bigint primary key not null,
  name VARCHAR(45)
);

Create Table user_entity (
  id BIGSERIAL primary key not null,
  full_name VARCHAR(255),
  password VARCHAR(255),
  persist_date timestamp,
  role_id bigint  not null,
  last_redaction_date timestamp,
  email VARCHAR(255),
  about TEXT,
  city VARCHAR(255),
  link_site VARCHAR(255),
  link_github VARCHAR(255),
  link_vk VARCHAR(255),
  is_enabled boolean,
  is_deleted boolean,
  image_link VARCHAR(255),
  nickname text,
  FOREIGN KEY (role_id) REFERENCES role(id)
);

Create Table question (
  id BIGSERIAL primary key not null,
  title VARCHAR(255) not null,
  description TEXT not null,
  persist_date timestamp,
  view_count INT,
  user_id bigint not null,
  is_deleted boolean,
  last_redaction_date timestamp,
  FOREIGN KEY (user_id) REFERENCES user_entity(id)
);

Create Table question_viewed (
  id BIGSERIAL primary key not null,
  user_id bigint not null,
  question_id bigint not null,
  persist_date timestamp,
  FOREIGN KEY (user_id) REFERENCES user_entity(id),
  FOREIGN KEY (question_id) REFERENCES question(id)
);

Create Table question_all_time (
  id BIGSERIAL primary key not null,
  user_id bigint not null,
  question_id bigint not null,
  persist_date timestamp,
  FOREIGN KEY (user_id) REFERENCES user_entity(id),
  FOREIGN KEY (question_id) REFERENCES question(id)
);

Create Table answer (
  id BIGSERIAL primary key not null,
  user_id bigint,
  question_id bigint,
  html_body TEXT,
  persist_date timestamp,
  is_helpful boolean,
  is_deleted boolean,
  is_deleted_by_moderator boolean,
  date_accept_time timestamp,
  update_date timestamp,
  FOREIGN KEY (user_id) REFERENCES user_entity(id),
  FOREIGN KEY (question_id) REFERENCES question(id)
);

Create Table votes_on_answers (
  id BIGSERIAL primary key not null,
  user_id bigint not null,
  answer_id bigint not null,
  persist_date timestamp,
  vote varchar(255),
  FOREIGN KEY (user_id) REFERENCES user_entity(id),
  FOREIGN KEY (answer_id) REFERENCES answer(id)
);

Create Table votes_on_questions (
  id BIGSERIAL primary key not null,
  user_id bigint not null,
  question_id bigint not null,
  persist_date timestamp,
  vote varchar(255),
  FOREIGN KEY (user_id) REFERENCES user_entity(id),
  FOREIGN KEY (question_id) REFERENCES question(id)
);

Create Table tag (
  id BIGSERIAL primary key not null,
  name VARCHAR(45),
  description TEXT,
  persist_date timestamp
);

Create Table tag_ignore (
  id BIGSERIAL primary key not null,
  ignored_tag_id bigint,
  user_id bigint,
  persist_date timestamp,
  FOREIGN KEY (user_id) REFERENCES user_entity(id),
  FOREIGN KEY (ignored_tag_id) REFERENCES tag(id)
  );

Create Table tag_tracked (
  id BIGSERIAL primary key not null,
  tracked_tag_id bigint,
  user_id bigint,
  persist_date timestamp,
  FOREIGN KEY (user_id) REFERENCES user_entity(id),
  FOREIGN KEY (tracked_tag_id ) REFERENCES tag(id)
);

Create Table question_has_tag (
  tag_id bigint not null,
  question_id bigint not null,
  FOREIGN KEY (tag_id) REFERENCES tag(id),
  FOREIGN KEY (question_id) REFERENCES question(id)

);

Create Table comment (
  id BIGSERIAL primary key not null,
  user_id bigint,
  text TEXT,
  persist_date timestamp,
  last_redaction_date timestamp,
  comment_type INT,
  FOREIGN KEY (user_id) REFERENCES user_entity(id)
);

Create Table user_favorite_question (
  id BIGSERIAL primary key not null,
  persist_date timestamp,
  user_id bigint not null,
  question_id bigint not null,
  FOREIGN KEY (user_id) REFERENCES user_entity(id),
  FOREIGN KEY (question_id) REFERENCES question(id)
);

Create Table comment_answer (
  comment_id bigint not null,
  answer_id bigint not null,
  FOREIGN KEY (comment_id) REFERENCES comment(id),
  FOREIGN KEY (answer_id) REFERENCES answer(id)
);

Create Table comment_question (
  comment_id bigint not null,
  question_id bigint not null,
  FOREIGN KEY (comment_id) REFERENCES comment(id),
  FOREIGN KEY (question_id) REFERENCES question(id)
);

Create Table related_tag (
  id BIGSERIAL primary key not null,
  child_tag bigint not null,
  main_tag bigint not null,
  FOREIGN KEY (child_tag) REFERENCES tag(id),
  FOREIGN KEY (main_tag) REFERENCES tag(id)
);

Create Table reputation (
  id BIGSERIAL primary key not null,
  count int,
  persist_date timestamp,
  type INT,
  author_id bigint,
  sender_id bigint,
  question_id bigint,
  answer_id bigint,
  FOREIGN KEY (question_id) REFERENCES question(id),
  FOREIGN KEY (author_id) REFERENCES user_entity(id),
  FOREIGN KEY (sender_id) REFERENCES user_entity(id),
  FOREIGN KEY (answer_id) REFERENCES answer(id)
);

Create Table badges (
  id BIGSERIAL primary key not null,
  badge_name VARCHAR(255),
  description VARCHAR(255),
  reputations_for_merit int
);

Create Table user_badges (
  id BIGSERIAL primary key not null,
  ready boolean,
  user_id bigint,
  badges_id bigint,
  FOREIGN KEY (user_id) REFERENCES user_entity(id),
  FOREIGN KEY (badges_id) REFERENCES badges(id)
);

Create Table chat (
  id BIGSERIAL primary key not null,
  chat_type INT,
  persist_date timestamp,
  title VARCHAR(255)
);

Create Table group_chat (
  chat_id bigint,
  FOREIGN KEY (chat_id) REFERENCES chat(id)
);

Create Table groupchat_has_users (
  chat_id bigint,
  user_id bigint,
  FOREIGN KEY (user_id) REFERENCES user_entity(id),
  FOREIGN KEY (chat_id) REFERENCES chat(id)
);

Create Table message (
  id BIGSERIAL primary key not null,
  last_redaction_date timestamp,
  message TEXT,
  persist_date timestamp,
  user_sender_id bigint,
  chat_id bigint,
  FOREIGN KEY (user_sender_id) REFERENCES user_entity(id),
  FOREIGN KEY (chat_id) REFERENCES chat(id)
);

Create Table single_chat (
  chat_id bigint,
  use_two_id bigint,
  user_one_id bigint,
  FOREIGN KEY (chat_id) REFERENCES chat(id),
  FOREIGN KEY (use_two_id) REFERENCES user_entity(id),
  FOREIGN KEY (user_one_id) REFERENCES user_entity(id)
);

Create Table bookmarks (
  id bigint primary key not null,
  question_id bigint,
  user_id bigint,
  FOREIGN KEY (user_id) REFERENCES user_entity(id),
  FOREIGN KEY (question_id) REFERENCES question(id)
);