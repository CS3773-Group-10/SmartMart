create table cartItems
(
  id        integer not null
    primary key autoincrement,
  custID    int
    references customers,
  productID int
    references products,
  quantity int
);

create unique index cartItems_id_uindex
  on cartItems (id);

INSERT INTO cartItems (id, custID, productID, quantity) VALUES (1, 1, 1, 2);
INSERT INTO cartItems (id, custID, productID, quantity) VALUES (2, 3, 2, 1);
INSERT INTO cartItems (id, custID, productID, quantity) VALUES (3, 1, 4, 1);
