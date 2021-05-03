create table orders
(
  id      integer not null
    primary key autoincrement,
  custID  int
    references customers,
  cardNum int,
  status  varchar
);

create unique index orders_id_uindex
  on orders (id);

INSERT INTO orders (id, custID, cardNum, status) VALUES (1, 1, 4444333322221111, 'Delivered');
