INSERT INTO Users (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE) VALUES
  ('Oxana', 'Name', 'oxana@gmail.com',  '$2a$10$eBV7al31hkDoFlro3r5CWuz.doYh4F4jgOqvWBgqoO9m48.CYrqZ6', 'USER'), -- pass: 123
  ('Alex', 'Lastname', 'alex@gmail.com', '$2a$10$ctvShkfWDty01ssKCN60G.oIkOvtzOhiuxSN0OgosixBijy48vtOO', 'ADMIN');  -- 456

INSERT INTO Items (USER_ID, DATE_TIME, TITLE, DESCRIPTION) VALUES
  (1, '2018-10-16 22:30:00', 'laptop', 'very nice condition'),
  (1, '2018-10-16 22:10:00', 'mouse', 'cool mouse'),
  (1, '2018-10-16 09:00:00', 'headphones contains laptop', 'wireless headphones'),
  (2, '2018-10-18 10:00:00', 'table', 'comfortable wooden table'),
  (2, '2018-10-25 09:40:00', 'Samsung S8', 'very good condition'),
  (2, '2018-10-20 09:00:00', 'iphone X10', 'used only 2 months, like new');

INSERT INTO Images (NAME, IMAGE_DATA, ITEM_ID, THUMBNAIL) VALUES
 ('image1.jpg', FILE_READ('src/main/resources/images/image1.jpg'), 1, true),
 ('image1.jpg', FILE_READ('src/main/resources/images/image1.jpg'), 1, false),
 ('image4.jpg', FILE_READ('src/main/resources/images/image4.jpg'), 1, false),
 ('image3.jpg', FILE_READ('src/main/resources/images/image3.jpg'), 1, false),

 ('image2.jpg', FILE_READ('src/main/resources/images/image2.jpg'), 2, true),
 ('image2.jpg', FILE_READ('src/main/resources/images/image2.jpg'), 2, false),

 ('noimage.png', FILE_READ('src/main/resources/images/noimage.png'), 3, true),

 ('image4.jpg', FILE_READ('src/main/resources/images/image4.jpg'), 4, true),
 ('image4.jpg', FILE_READ('src/main/resources/images/image4.jpg'), 4, false),

 ('verticalimage.jpg', FILE_READ('src/main/resources/images/verticalimage.jpg'), 5, true),
 ('verticalimage.jpg', FILE_READ('src/main/resources/images/verticalimage.jpg'), 5, false),

 ('noimage.png', FILE_READ('src/main/resources/images/noimage.png'), 6, true);

INSERT INTO Rating (RATING, ITEM_ID, USER_ID) VALUES
 (4.5, 1, 1), (3.0, 1, 2),
 (5.0, 2, 1),
 (5.0, 5, 1);

INSERT INTO Comments (COMMENT, DATE_TIME, ITEM_ID, USER_ID) VALUES
 ('Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore',
   '2018-10-16 22:30:00', 1, 1),
 ('this is my comment', '2018-10-20 10:30:00', 1, 2),
 ('this is my comment2', '2018-10-20 10:30:00', 2, 1),;


