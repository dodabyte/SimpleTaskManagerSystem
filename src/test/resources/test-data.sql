INSERT INTO taskserviceapp.users (user_id, username, full_name, email, password, registration_date, role)
VALUES ('2b5f2fee-e73f-467f-8399-235e9d4e473d','test2','Тестов2 Тест2 Тестович2','test2@example.com',
        '$2a$10$89jmiRa.2X5FZX26zv91j.VBLMLAvkwKwI9VoiA9seTik0ZNkGO3G','2024-08-29 18:52:26.918578','ROLE_USER'),
       ('e71a25f6-aea7-4b3e-a0c3-4628e454ad1f','test','Тестов Тест Тестович','test@example.com',
        '$2a$10$eqR4GFPrh82zsUTR50woFeN.PL59i6m7jMR6KwF.WBwyJR6ixLZjS','2024-09-13 14:09:21.531306','ROLE_USER');

INSERT INTO taskserviceapp.tasks (task_id, author_id, executor_id, title, description, status, priority)
VALUES ('a474a9e2-ee33-4bc2-bdc5-fe88ecdfb3a3','2b5f2fee-e73f-467f-8399-235e9d4e473d',
        '2b5f2fee-e73f-467f-8399-235e9d4e473d','Багфикс TaskService',null,'WAITING','MEDIUM'),
       ('79d9c322-73dc-4e53-aad6-28c408188c5a','e71a25f6-aea7-4b3e-a0c3-4628e454ad1f',
        '2b5f2fee-e73f-467f-8399-235e9d4e473d','Настройка Kafka',null,'WAITING','HIGH');

INSERT INTO taskserviceapp.comments (comment_id, task_id, author_id, text, comment_time, modification_time)
VALUES ('34b13ef6-3abf-4f20-bcfb-7d9562a2022d','a474a9e2-ee33-4bc2-bdc5-fe88ecdfb3a3',
        '2b5f2fee-e73f-467f-8399-235e9d4e473d','Я помогу тебе с задачей ;)','2024-08-30 19:25:02.994020',null),
       ('24136f87-a9db-460c-b756-38688bb1c8a4','a474a9e2-ee33-4bc2-bdc5-fe88ecdfb3a3',
        '2b5f2fee-e73f-467f-8399-235e9d4e473d','опана','2024-08-29 19:23:24.693127',null);