create table cartItems
(
  id        integer not null
    primary key autoincrement,
  custID    int
    references customers,
  productID int
    references products
);

create unique index cartItems_id_uindex
  on cartItems (id);

INSERT INTO cartItems (id, custID, productID) VALUES (1, 0, 0);
INSERT INTO cartItems (id, custID, productID) VALUES (2, 3, 2);
INSERT INTO cartItems (id, custID, productID) VALUES (3, 0, 4);
