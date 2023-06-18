
-- Theme Path table initialization
INSERT INTO THEMED_PATH (theme) VALUES ('AI');
INSERT INTO THEMED_PATH (theme) VALUES ('DATABASES');
INSERT INTO THEMED_PATH (theme) VALUES ('GAMEDEV');

-- Lectures of AI Theme Path table initialization
INSERT INTO LECTURE (themed_path_id, start_time, end_time, topic)
VALUES (SELECT id from THEMED_PATH WHERE theme='AI',
            '2023-06-01 10:00:00',
            '2023-06-01 11:45:00',
            'ChatGPT: Pros and Cons' );
INSERT INTO LECTURE (themed_path_id, start_time, end_time, topic)
VALUES (SELECT id from THEMED_PATH WHERE theme='AI',
            '2023-06-01 12:00:00',
            '2023-06-01 13:45:00',
            'Stable Diffusion image generation' );
INSERT INTO LECTURE (themed_path_id, start_time, end_time, topic)
VALUES (SELECT id from THEMED_PATH WHERE theme='AI',
            '2023-06-01 14:00:00',
            '2023-06-01 15:45:00',
            'Generative adversarial networks' );

-- Lectures of DATABASES Theme Path table initialization
INSERT INTO LECTURE (themed_path_id, start_time, end_time, topic)
VALUES (SELECT id from THEMED_PATH WHERE theme='DATABASES',
               '2023-06-01 10:00:00',
               '2023-06-01 11:45:00',
               'Advanced SQL use cases' );
INSERT INTO LECTURE (themed_path_id, start_time, end_time, topic)
VALUES (SELECT id from THEMED_PATH WHERE theme='DATABASES',
               '2023-06-01 12:00:00',
               '2023-06-01 13:45:00',
               'ETL-based data warehousing' );
INSERT INTO LECTURE (themed_path_id, start_time, end_time, topic)
VALUES (SELECT id from THEMED_PATH WHERE theme='DATABASES',
               '2023-06-01 14:00:00',
               '2023-06-01 15:45:00',
               'SQL vs NoSQL: What is better for your projects?' );

-- Lectures of GAMEDEV Theme Path table initialization
INSERT INTO LECTURE (themed_path_id, start_time, end_time, topic)
VALUES (SELECT id from THEMED_PATH WHERE theme='GAMEDEV',
               '2023-06-01 10:00:00',
               '2023-06-01 11:45:00',
               'MetaHuman Plugin for Unreal Engine' );
INSERT INTO LECTURE (themed_path_id, start_time, end_time, topic)
VALUES (SELECT id from THEMED_PATH WHERE theme='GAMEDEV',
               '2023-06-01 12:00:00',
               '2023-06-01 13:45:00',
               'Use DOTween effectively' );
INSERT INTO LECTURE (themed_path_id, start_time, end_time, topic)
VALUES (SELECT id from THEMED_PATH WHERE theme='GAMEDEV',
               '2023-06-01 14:00:00',
               '2023-06-01 15:45:00',
               'Unity mobile game development' );