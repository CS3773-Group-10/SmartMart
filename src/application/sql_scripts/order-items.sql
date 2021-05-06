create table orderItems
(
  id        integer not null
    primary key autoincrement,
  orderID   int     not null
    references orders,
  productID int     not null
    references products,
  quantity int
);

create unique index orderItems_id_uindex
  on orderItems (id);

INSERT INTO orderItems (id, orderID, productID, quantity) VALUES (1, 1, 2, 1);
INSERT INTO orderItems (id, orderID, productID, quantity) VALUES (2, 1, 8, 2);
INSERT INTO orderItems (id, orderID, productID, quantity) VALUES (3, 1, 11, 1);
