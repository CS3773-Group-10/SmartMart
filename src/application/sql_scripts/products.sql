create table products
(
    id          integer not null
primary key autoincrement,
    name        varchar not null,
    description varchar,
    category    varchar not null,
    quantity    int default 0 not null,
    sellBy      date
);

INSERT INTO products (id, name, description, category, quantity, sellBy) VALUES (1, 'Apples', 'Fresh and juicy red apples', 'Fruits', 6, null);
INSERT INTO products (id, name, description, category, quantity, sellBy) VALUES (2, 'Bananas', 'Sweet bananas', 'Fruits', 3, null);
INSERT INTO products (id, name, description, category, quantity, sellBy) VALUES (3, 'Carrots', 'Bag of carrots', 'Vegetables', 10, null);
INSERT INTO products (id, name, description, category, quantity, sellBy) VALUES (4, 'Broccoli', null, 'Vegetables', 7, null);
INSERT INTO products (id, name, description, category, quantity, sellBy) VALUES (5, 'Milk', 'Gallon of cow''s milk', 'Dairy', 3, '1622523600000');
INSERT INTO products (id, name, description, category, quantity, sellBy) VALUES (6, 'Cheddar Cheese', 'Block of cheddar cheese', 'Dairy', 2, '1622523600000');
INSERT INTO products (id, name, description, category, quantity, sellBy) VALUES (7, 'Sour Cream', '6oz tub of sour cream', 'Dairy', 4, '1623474000000');
INSERT INTO products (id, name, description, category, quantity, sellBy) VALUES (8, 'Chicken', 'Oven ready rotisserie', 'Meat', 5, null);
INSERT INTO products (id, name, description, category, quantity, sellBy) VALUES (9, 'Ground Beef', '1lb ground beef', 'Meat', 3, null);
INSERT INTO products (id, name, description, category, quantity, sellBy) VALUES (10, 'Bacon', '20 strips of delicious bacon', 'Meat', 7, null);
INSERT INTO products (id, name, description, category, quantity, sellBy) VALUES (11, 'Sausage Links', 'Pork sausage links', 'Meat', 11, null);
INSERT INTO products (id, name, description, category, quantity, sellBy) VALUES (12, 'Chicken Stock', 'From Campbells', 'Canned Goods', 12, null);
INSERT INTO products (id, name, description, category, quantity, sellBy) VALUES (13, 'Spam', null, 'Canned Goods', 12, null);
INSERT INTO products (id, name, description, category, quantity, sellBy) VALUES (14, 'Beans', '', 'Canned Goods', 10, null);
INSERT INTO products (id, name, description, category, quantity, sellBy) VALUES (15, 'Crackers', 'Saltine crackers', 'Snacks', 7, null);
INSERT INTO products (id, name, description, category, quantity, sellBy) VALUES (16, 'Chips', 'Salty Potato chips', 'Snacks', 11, null);
INSERT INTO products (id, name, description, category, quantity, sellBy) VALUES (17, 'Cookies', 'Chocolate Chip Cookies', 'Snacks', 4, null);
