create table orderItems
(
  id        integer not null
    primary key autoincrement,
  orderID   int     not null
    references orders,
  productID int     not null
    references products
);

create unique index orderItems_id_uindex
  on orderItems (id);

INSERT INTO orderItems (id, orderID, productID) VALUES (1, 1, 2);
INSERT INTO orderItems (id, orderID, productID) VALUES (2, 1, 8);
INSERT INTO orderItems (id, orderID, productID) VALUES (3, 1, 11);
