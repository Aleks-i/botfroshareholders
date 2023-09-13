DROP TABLE IF EXISTS anecdotes;
DROP SEQUENCE id_anecdotes_seq;
DROP TABLE IF EXISTS statuses;
DROP SEQUENCE id_statuses_seq;

-- CREATE TYPE chat_types AS ENUM ('PRIVATE', 'GROUP', 'CHANNEL');

CREATE SEQUENCE id_anecdotes_seq START WITH 1;
CREATE SEQUENCE id_statuses_seq START WITH 1;

CREATE TABLE anecdotes
(
    id           INTEGER PRIMARY KEY DEFAULT nextval('id_anecdotes_seq'),
    anecdote         TEXT                               NOT NULL
);

CREATE TABLE statuses
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('id_statuses_seq'),
    status           TEXT                           NOT NULL
);

