create table product (
    id varchar(100) primary key,
    code varchar(10) not null unique,
    name varchar(255) not null,
    price decimal(19,2) not null
) Engine=InnoDB;