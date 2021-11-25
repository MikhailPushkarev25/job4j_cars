
create table car (
   id serial primary key,
   mark varchar(255)
);

create table bodyType (
    id serial primary key,
    name varchar(255)
);

create table info (
    id serial primary key,
    description varchar(255),
    photo varchar(255),
    car_id int references car(id),
    bodyType_id int references bodyType(id)
);

create table users (
    id serial primary key,
    name varchar(255),
    email varchar(255),
    password varchar(255)
);