CREATE TABLE products (
  id BIGSERIAL primary key,
  name varchar(100) not null,
  description varchar(200),
  price DECIMAL(10,2) not null
);
