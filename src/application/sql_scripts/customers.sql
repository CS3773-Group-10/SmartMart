create table customers
(
	id integer primary key autoincrement not null,
	firstName varchar not null,
	lastName varchar not null,
	email varchar not null,
	password varchar not null,
	address varchar
);

create unique index customers_email_uindex
	on customers (email);

create unique index customers_id_uindex
	on customers (id);

INSERT INTO customers (id, firstName, lastName, email, password, address) VALUES (1, 'John', 'Foo', 'john@foo.com', 'foo', '123 Sesame Street');
INSERT INTO customers (id, firstName, lastName, email, password, address) VALUES (2, 'Anna', 'Adams', 'aadams@gmail.com', 'bar', null);
INSERT INTO customers (id, firstName, lastName, email, password, address) VALUES (3, 'Jesse', 'Griffin', 'jgriffin@gmail.com', 'pass', null);
INSERT INTO customers (id, firstName, lastName, email, password, address) VALUES (4, 'Henry', 'Moore', 'hmoore@gmail.com', 'pass', null);
INSERT INTO customers (id, firstName, lastName, email, password, address) VALUES (5, 'Willie', 'Gonzales', 'willg@gmail.com', 'pass', null);
INSERT INTO customers (id, firstName, lastName, email, password, address) VALUES (6, 'Kimberly', 'Diaz', 'kimdiaz@gmail.com', 'pass', null);
INSERT INTO customers (id, firstName, lastName, email, password, address) VALUES (7, 'Peter', 'Johnson', 'thepj@gmail.com', 'pass', null);
