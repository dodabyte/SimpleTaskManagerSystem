--liquibase formatted sql
--changeset Vadim_Miller:v1.0 localFilePath:01.000.00/tasks.sql

CREATE TABLE tasks
(
    task_id uuid PRIMARY KEY,
    author_id uuid NOT NULL,
    executor_id uuid,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(100),
    status VARCHAR(11) CHECK ( status IN ('WAITING', 'IN_PROGRESS', 'DONE')),
    priority VARCHAR(6) NOT NULL CHECK ( priority IN ('HIGH', 'MEDIUM', 'LOW')),
    FOREIGN KEY (author_id) REFERENCES users(user_id),
    FOREIGN KEY (executor_id) REFERENCES users(user_id)
);

--rollback drop table tasks;