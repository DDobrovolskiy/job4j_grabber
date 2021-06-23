CREATE TABLE meetings (
	meeting_id serial PRIMARY KEY,
	name text NOT NULL
);

INSERT INTO meetings (name) VALUES
('Kripto security'),
('Java professional'),
('How cookie koffe'),
('Boss ask you');

CREATE TABLE users (
	user_id serial PRIMARY KEY,
	name varchar(255) NOT NULL
);

INSERT INTO users (name) VALUES
('Charlie Shin'),
('Bill Mirue'),
('Vasiliy Pupkin');

CREATE TABLE statuses (
	status_id serial PRIMARY KEY,
	name varchar(255) NOT NULL
);

INSERT INTO statuses VALUES
(1, 'will'),
(2, 'out');

CREATE TABLE records (
	meeting_id int NOT NULL,
	user_id int NOT NULL,
	status_id int NOT NULL,
	UNIQUE (meeting_id, user_id)
);

INSERT INTO records VALUES
(1, 1, 1),
(1, 2, 1),
(1, 3, 2),
(2, 1, 1),
(2, 2, 1),
(2, 3, 1),
(3, 1, 2),
(3, 2, 2),
(3, 3, 2);

--Zadanie 1
--Variant 1
SELECT name, SUM(
	CASE WHEN status_id = 1 THEN 1
	ELSE 0
	END) AS users_will
FROM meetings
LEFT JOIN records USING (meeting_id)
GROUP BY (name)
ORDER BY users_will DESC;

--Variant 2
SELECT name, COUNT(*)
FROM meetings
JOIN records USING (meeting_id)
WHERE status_id = 1
GROUP BY (name)
ORDER BY COUNT(*) DESC;

--Zadanie 2
SELECT name
FROM meetings
LEFT JOIN records USING (meeting_id)
GROUP BY (name)
HAVING (SUM(
	CASE WHEN status_id = 1 THEN 1
	ELSE 0
	END) = 0);
