DROP TABLE IF EXISTS sender_type;
DROP TABLE IF EXISTS chats;
DROP SEQUENCE IF EXISTS id_chats_seq;
DROP TYPE IF EXISTS chat_types;

CREATE SEQUENCE id_chats_seq START WITH 1;

CREATE TABLE chats
(
    id               BIGINT PRIMARY KEY DEFAULT nextval('id_chats_seq'),
    chat_id          BIGINT                     NOT NULL,
    type_chat        VARCHAR                    NOT NULL
);

CREATE TABLE sender_type
(
    chat_id          BIGINT                     NOT NULL,
    type_chat        VARCHAR                    NOT NULL,
    is_active        BOOLEAN                    NOT NULL,
    FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE
);

