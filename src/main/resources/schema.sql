DROP TABLE IF EXISTS todos;

CREATE TABLE todos (
id SERIAL PRIMARY KEY,
todo VARCHAR(250),
valmis BOOLEAN NOT NULL
);