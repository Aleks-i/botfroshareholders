DROP TABLE IF EXISTS mailer_type;
DROP TABLE IF EXISTS chats;
DROP SEQUENCE IF EXISTS id_chats_seq;

CREATE SEQUENCE id_chats_seq START WITH 1;

CREATE TABLE chats
(
    id               BIGINT PRIMARY KEY DEFAULT nextval('id_chats_seq'),
    chat_id          BIGINT                     NOT NULL,
    chat_type        VARCHAR                    NOT NULL,
    user_name        VARCHAR,
    first_name       VARCHAR,
    last_name        VARCHAR
);

CREATE TABLE mailer_type
(
    chat_id          BIGINT                     NOT NULL,
    type             VARCHAR                    NOT NULL,
    is_active        BOOLEAN                    NOT NULL,
    FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE
);

