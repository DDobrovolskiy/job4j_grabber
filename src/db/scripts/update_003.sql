CREATE TABLE IF NOT EXISTS rabbit (
	rabbit_id serial PRIMARY KEY,
	created_date timestamp
);

	CREATE TABLE IF NOT EXISTS posts (
	post_id serial PRIMARY KEY,
	name varchar(1024) NOT NULL,
	text text NOT NULL,
	link varchar(1024) NOT NULL,
	UNIQUE(link)
);