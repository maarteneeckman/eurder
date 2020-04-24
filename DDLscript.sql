create schema if not exists eurder;
set schema 'eurder';

create table if not exists customer (
	customerid uuid primary key,
	firstname varchar(50),
	lastname varchar(50),
	email varchar(50),
	street varchar(50),
	housenumber integer,
	city varchar(50),
	postcode integer
);

create table if not exists item (
	itemid uuid primary key,
	name varchar(50),
	description varchar,
	price numeric(6,2),
	amountinstock integer
);

create table if not exists order_table (
	orderid uuid primary key,
	customerid uuid references customer(customerid)
);

create table if not exists itemgroup(
	itemid uuid references item(itemid),
	orderid uuid references order_table(orderid),
	amount integer,
	price numeric(7,2),
	shippingdate date,
	primary key (itemid, orderid)
);

