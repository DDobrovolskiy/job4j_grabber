CREATE TABLE IF NOT EXISTS rabbit (
	rabbit_id serial PRIMARY KEY,
	created_date timestamp
);

CREATE TABLE IF NOT EXISTS posts (
post_id serial PRIMARY KEY,
name varchar(1024),
text text,
link varchar(1024) unique
);