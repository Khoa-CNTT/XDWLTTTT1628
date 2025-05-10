INSERT INTO test_years (year) VALUES (2023);
INSERT INTO toeic_tests (year_id, title, description, total_questions, time_limit, is_free, is_published, is_placement_test, created_at)
VALUES (1, 'TOEIC Test 2023', 'Sample TOEIC test for practice', 100, 7200, false, true, false, NOW());
INSERT INTO test_sections (test_id, section_type, section_part, title, instructions)
VALUES (1, 'LISTENING', 1, 'Listening Part 1', 'Listen to the audio and answer the questions.');
INSERT INTO users (username, email, password, full_name, status, created_at)
VALUES ('john_doe', 'john@example.com', 'hashed_password', 'John Doe', 'ACTIVE', NOW());