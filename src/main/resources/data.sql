INSERT INTO Users (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES
  ('Oxana', 'Dunav', 'oxana@gmail.com',  '$2a$10$eBV7al31hkDoFlro3r5CWuz.doYh4F4jgOqvWBgqoO9m48.CYrqZ6'), -- pass: 123
  ('Alex', 'Lastname', 'alex@gmail.com', '$2a$10$ctvShkfWDty01ssKCN60G.oIkOvtzOhiuxSN0OgosixBijy48vtOO');  -- 456

INSERT INTO Items (USER_ID, DATE_TIME, TITLE, DESCRIPTION) VALUES
  (1, '2018-10-16 22:30:00', 'laptop', 'very nice condition'),
  (1, '2018-10-16 09:00:00', 'mouse', 'cool mouse');

INSERT INTO Images (NAME, IMAGE_DATA, ITEM_ID, THUMBNAIL) VALUES
 ('image1.jpg', FILE_READ('src/main/resources/images/image1.jpg'), 1, true),
 ('image1.jpg', FILE_READ('src/main/resources/images/image1.jpg'), 1, false),
 ('image4.jpg', FILE_READ('src/main/resources/images/image4.jpg'), 1, false),
 ('image3.jpg', FILE_READ('src/main/resources/images/image3.jpg'), 1, false),
 ('image2.jpg', FILE_READ('src/main/resources/images/image2.jpg'), 2, true),
 ('image2.jpg', FILE_READ('src/main/resources/images/image2.jpg'), 2, false);


