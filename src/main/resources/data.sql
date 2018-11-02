INSERT INTO Users (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE) VALUES
  ('Oxana', 'Name', 'oxana@gmail.com',  '$2a$10$eBV7al31hkDoFlro3r5CWuz.doYh4F4jgOqvWBgqoO9m48.CYrqZ6', 'USER'), -- pass: 123
  ('Alex', 'Lastname', 'alex@gmail.com', '$2a$10$ctvShkfWDty01ssKCN60G.oIkOvtzOhiuxSN0OgosixBijy48vtOO', 'ADMIN');  -- 456

INSERT INTO Items (USER_ID, DATE_TIME, TITLE, DESCRIPTION) VALUES
  (1, '2018-10-16 22:30:00', 'Apple MacBook Pro 15.4-Inch Laptop', 'Macbook-ul de 12 inch este cel mai atractiv model de laptop Apple în materie de preţ. Memoria RAM de 8 GB, hard disk SSD de până la 512 GB, autonomia bateriei de 10 ore.'),
  (1, '2018-05-12 22:10:00', 'NIKE Women Benassi Just Do It Synthetic Sandal', 'Women NIKE benassi just do it. Sandal features a lined upper with a bold logo for plush comfort and an athletic look. A foam-infused midsole and outsole provide lightweight impact protection.'),
  (1, '2018-06-16 09:00:00', 'Double Bass with an Adjustable Bridge', 'Package Includes: Padded nylon soft case with accessories pockets Brazilwood bow with ebony frog and unbleached genuine Mongolian horsehair High quality rosin cake.'),
  (2, '2018-01-18 10:00:00', '2 Slice Toaster, Cool Touch Stainless Steel Toaster', 'Whether you are preparing a sandwich or cheeses bagel, this reliable toasting gives you a crispy result every time, it is fast, efficient and user-friendly.'),
  (2, '2018-10-25 09:40:00', 'No Image Example', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam malesuada porttitor lobortis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse vel velit arcu. Nunc mi tellus'),
  (2, '2018-10-20 09:34:00', '100 Cotton Work Jeans with Triple Seams', 'We are manufacturers of specialized industrial and commercial work wear, providing protection and comfort to workers in many industries with our heavy duty garments.'),
  (2, '2018-09-07 11:30:00', 'JBL Xtreme 2 Portable Bluetooth Waterproof Speaker', 'JBL Xtreme 2 is the ultimate portable Bluetooth speaker that effortlessly delivers dynamic and immersive stereo sound. The speaker is armed with four drivers.'),
  (2, '2018-03-14 07:12:00', '12V Cordless Drill Diver', 'The Prostormer 12V Lithium-Ion Cordless 3/8-inch Drill Driver is engineered for applications in drilling wood, metal, masonry, etc.'),
  (2, '2018-06-16 10:01:00', 'Catan Expansion: Traders & Barbarians', 'Delve deep into Catan! In Catan: Traders & Barbarians youll find lots of cool new ways to explore Klaus Teubers award-winning game series. You can now play with just 2 players! Add a harbormaster, a friendly robber, or special events. ');


INSERT INTO Images (NAME, IMAGE_DATA, ITEM_ID, THUMBNAIL) VALUES

 ('mac.jpg', FILE_READ('src/main/resources/images/mac.jpg'), 1, true),
 ('mac.jpg', FILE_READ('src/main/resources/images/mac1.jpg'), 1, false),
 ('mac1.jpg', FILE_READ('src/main/resources/images/mac1.jpg'), 1, false),
 ('mac3.jpg', FILE_READ('src/main/resources/images/mac3.jpg'), 1, false),
 ('mac4.jpg', FILE_READ('src/main/resources/images/mac4.jpg'), 1, false),

 ('nike.jpg', FILE_READ('src/main/resources/images/nike.jpg'), 2, true),
 ('nike.jpg', FILE_READ('src/main/resources/images/nike.jpg'), 2, false),
 ('nike1.jpg', FILE_READ('src/main/resources/images/nike1.jpg'), 2, false),
 ('nike2.jpg', FILE_READ('src/main/resources/images/nike2.jpg'), 2, false),
 ('nike3.jpg', FILE_READ('src/main/resources/images/nike3.jpg'), 2, false),

 ('bass.jpg', FILE_READ('src/main/resources/images/bass.jpg'), 3, true),
 ('bass.jpg', FILE_READ('src/main/resources/images/bass.jpg'), 3, false),
 ('bass1.jpg', FILE_READ('src/main/resources/images/bass1.jpg'), 3, false),
 ('bass2.jpg', FILE_READ('src/main/resources/images/bass2.jpg'), 3, false),
 ('bass3.jpg', FILE_READ('src/main/resources/images/bass3.jpg'), 3, false),

 ('toast.jpg', FILE_READ('src/main/resources/images/toast.jpg'), 4, true),
 ('toast.jpg', FILE_READ('src/main/resources/images/toast.jpg'), 4, false),
 ('toast1.jpg', FILE_READ('src/main/resources/images/toast1.jpg'), 4, false),
 ('toast2.jpg', FILE_READ('src/main/resources/images/toast2.jpg'), 4, false),
 ('toast3.jpg', FILE_READ('src/main/resources/images/toast3.jpg'), 4, false),

 ('noimage.png', FILE_READ('src/main/resources/images/noimage.png'), 5, true),

 ('jeans.jpg', FILE_READ('src/main/resources/images/jeans.jpg'), 6, true),
 ('jeans.jpg', FILE_READ('src/main/resources/images/jeans.jpg'), 6, false),
 ('jeans1.jpg', FILE_READ('src/main/resources/images/jeans1.jpg'), 6, false),
 ('jeans2.jpg', FILE_READ('src/main/resources/images/jeans2.jpg'), 6, false),
 ('jeans3.jpg', FILE_READ('src/main/resources/images/jeans3.jpg'), 6, false),

  ('jbl.jpg', FILE_READ('src/main/resources/images/jbl.jpg'), 7, true),
  ('jbl.jpg', FILE_READ('src/main/resources/images/jbl.jpg'), 7, false),
  ('jbl1.jpg', FILE_READ('src/main/resources/images/jbl1.jpg'), 7, false),
  ('jbl2.jpg', FILE_READ('src/main/resources/images/jbl2.jpg'), 7, false),
  ('jbl3.jpg', FILE_READ('src/main/resources/images/jbl3.jpg'), 7, false),

  ('drill.jpg', FILE_READ('src/main/resources/images/drill.jpg'), 8, true),
  ('drill.jpg', FILE_READ('src/main/resources/images/drill.jpg'), 8, false),
  ('drill1.jpg', FILE_READ('src/main/resources/images/drill1.jpg'), 8, false),
  ('drill2.jpg', FILE_READ('src/main/resources/images/drill2.jpg'), 8, false),
  ('drill3.jpg', FILE_READ('src/main/resources/images/drill3.jpg'), 8, false),

  ('catan.jpg', FILE_READ('src/main/resources/images/catan.jpg'), 9, true),
  ('catan.jpg', FILE_READ('src/main/resources/images/catan.jpg'), 9, false),
  ('catan1.jpg', FILE_READ('src/main/resources/images/catan1.jpg'), 9, false),
  ('catan2.jpg', FILE_READ('src/main/resources/images/catan2.jpg'), 9, false),
  ('catan4.jpg', FILE_READ('src/main/resources/images/catan4.jpg'), 9, false);


INSERT INTO Rating (RATING, ITEM_ID, USER_ID) VALUES
 (4.0, 1, 1), (3.0, 1, 2),
 (5.0, 2, 1), (3.0, 2, 2),
 (5.0, 3, 1), (2.0, 3, 2),
 (3.0, 4, 1), (5.0, 4, 2),
 (2.0, 5, 1), (4.0, 5, 2),
 (2.0, 6, 1), (1.0, 6, 2),
 (4.0, 7, 1), (4.0, 7, 2),
 (5.0, 9, 1), (5.0, 9, 2);


INSERT INTO Comments (COMMENT, DATE_TIME, ITEM_ID, USER_ID) VALUES
 ('I was a little skeptical at first but I’m sooooo glad I bought it', '2018-10-16 01:00:00', 1, 1),
 ('I will never buy new again!! This computer is great!', '2018-10-16 02:00:00', 1, 2),

 ('Love these! The bright color was just what I was looking for! Super comfy and fit perfect!', '2018-10-16 03:00:00', 2, 1),
 ('I have had Nike slip ons for years. I love them and they always fit. These are horrible they are too small and thinner they any I have ever had.', '2018-10-16 04:00:00', 2, 2),

 ('I knew what I was getting into buying a cheap upright bass from Amazon.', '2018-10-16 05:00:00', 3, 1),
 ('I brought this double bass for my daughter''s 16th birthday. this bass is so beautiful in person than on the picture shown.', '2018-10-16 06:00:00', 3, 2),

 ('I am 64 years old and have had many toasters in my life. I have never had one that smelled as noxious as this the first 5 ! times I heated it to "get off the manufacturing oils" as the instructions say. I have opened the windows and put on a fan.', '2018-10-16 07:00:00', 4, 1),
 ('This toaster is so slow, and even on the darkest setting, the bread is only slightly brown around the edges. ', '2018-10-16 08:00:00', 4, 2),

 ('No comment', '2018-10-16 09:00:00', 5, 1),
 ('No comment', '2018-10-16 10:00:00', 5, 2),

 ('Im not for leaving reviews but when it’s something I use a lot I wish someone would do the same for me someday. ', '2018-10-16 11:00:00', 6, 1),
 ('Not bad for less expensive jeans, but hardly the work horse the name implies.', '2018-10-16 12:00:00', 6, 2),

 ('I want to say first that this speaker rocks! It is loud with great quality sound. It only lasts about 6 hours with the volume all the way up.', '2018-10-16 13:00:00', 7, 1),
 ('Recharging Time: About 2 hours, sometimes less.', '2018-10-16 14:00:00', 7, 2),

 ('Cool expansion but its basically an assortment of mini expanions. There''s some expansions that can fit into any typical game of base Catan plus Seafarers but some you have to play a game specifically for that expansion.', '2018-10-16 15:00:00', 9, 1),
 ('I like Catan. So, I bought this as my first expansion. I pleasantly surprised that this includes multiple variants and scenarios, that makes Catan better. ', '2018-10-16 16:00:00', 9, 2),;



