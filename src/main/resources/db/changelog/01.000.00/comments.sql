--liquibase formatted sql
--changeset Vadim_Miller:v1.0 localFilePath:01.000.00/comments.sql

CREATE TABLE comments
(
    comment_id uuid PRIMARY KEY,
    task_id uuid NOT NULL,
    author_id uuid NOT NULL,
    text VARCHAR(255) NOT NULL,
    comment_time TIMESTAMP NOT NULL,
    modification_time TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(user_id),
    FOREIGN KEY (task_id) REFERENCES tasks(task_id)
);

--rollback drop table comments;