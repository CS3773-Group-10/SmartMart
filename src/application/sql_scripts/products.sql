create table products
(
  id          integer not null
    primary key autoincrement,
  name        varchar not null,
  description varchar,
  category    varchar not null,
  price       int not null,
  quantity    int default 0 not null,
  sellBy      date
);

INSERT INTO products (id, name, description, category, quantity, sellBy, price) VALUES (1, 'Apples', 'Fresh and juicy red apples', 'Fruits', 6, null, 100);
INSERT INTO products (id, name, description, category, quantity, sellBy, price) VALUES (2, 'Bananas', 'Sweet bananas', 'Fruits', 3, null, 120);
INSERT INTO products (id, name, description, category, quantity, sellBy, price) VALUES (3, 'Carrots', 'Bag of carrots', 'Vegetables', 10, null, 300);
INSERT INTO products (id, name, description, category, quantity, sellBy, price) VALUES (4, 'Broccoli', 'Fresh green broccoli', 'Vegetables', 7, null, 260);
INSERT INTO products (id, name, description, category, quantity, sellBy, price) VALUES (5, 'Milk', 'Gallon of cow''s milk', 'Dairy', 3, '1622523600000', 520);
INSERT INTO products (id, name, description, category, quantity, sellBy, price) VALUES (6, 'Cheddar Cheese', 'Block of cheddar cheese', 'Dairy', 2, '1622523600000', 120);
INSERT INTO products (id, name, description, category, quantity, sellBy, price) VALUES (7, 'Sour Cream', '6oz tub of sour cream', 'Dairy', 4, '1623474000000', 200);
INSERT INTO products (id, name, description, category, quantity, sellBy, price) VALUES (8, 'Chicken', 'Oven ready rotisserie', 'Meat', 5, null, 610);
INSERT INTO products (id, name, description, category, quantity, sellBy, price) VALUES (9, 'Ground Beef', '1lb ground beef', 'Meat', 3, null, 550);
INSERT INTO products (id, name, description, category, quantity, sellBy, price) VALUES (10, 'Bacon', '20 strips of delicious bacon', 'Meat', 7, null, 480);
INSERT INTO products (id, name, description, category, quantity, sellBy, price) VALUES (11, 'Sausage Links', 'Pork sausage links', 'Meat', 11, null, 460);
INSERT INTO products (id, name, description, category, quantity, sellBy, price) VALUES (12, 'Cereal', 'Its just boring cereal', 'Grains', 12, null, 230);
INSERT INTO products (id, name, description, category, quantity, sellBy, price) VALUES (13, 'White Bread', 'Wonderous white bread loaf', 'Grains', 12, null, 300);
INSERT INTO products (id, name, description, category, quantity, sellBy, price) VALUES (14, 'Wheat Bread', 'A healthier bread load', 'Grains', 10, null, 400);
INSERT INTO products (id, name, description, category, quantity, sellBy, price) VALUES (15, 'Crackers', 'Saltine crackers', 'Snacks', 7, null, 100);
INSERT INTO products (id, name, description, category, quantity, sellBy, price) VALUES (16, 'Chips', 'Salty Potato chips', 'Snacks', 11, null, 170);
INSERT INTO products (id, name, description, category, quantity, sellBy, price) VALUES (17, 'Cookies', 'Chocolate Chip Cookies', 'Snacks', 4, null, 200);

