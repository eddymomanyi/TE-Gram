BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('IGotThemFlyShoes','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('CeilingCantHoldMe','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');

INSERT INTO posts (username, photo_url, likes, caption, date_time)
VALUES ('user', 'https://res.cloudinary.com/depdfgnxo/image/upload/v1675111135/sample.jpg', 3, 'Caption Uno', '2023-02-01 15:30:25-05');
INSERT INTO posts (username, photo_url, likes, caption, date_time)
VALUES ('user', 'https://res.cloudinary.com/depdfgnxo/image/upload/v1675813272/rh7sfblcmnhgzyjvsmcs.webp', 3, 'Woooo coachella was so much fun', '2023-02-01 15:45:25-05');
INSERT INTO posts (username, photo_url, likes, caption, date_time)
VALUES ('user', 'https://res.cloudinary.com/depdfgnxo/image/upload/v1676058627/ro9ukurech9z74i8u3xo.jpg', 2, 'My first time at Holi. What a colorful event. It was fun getting messy!', '2023-02-03 15:30:25-05');
INSERT INTO posts (username, photo_url, likes, caption, date_time)
VALUES ('IGotThemFlyShoes', 'https://res.cloudinary.com/depdfgnxo/image/upload/v1675111162/cld-sample-5.jpg', 5, 'These are clean, right? I just bought em' , '2023-02-02 17:45:25-05');
INSERT INTO posts (username, photo_url, likes, caption, date_time)
VALUES ('IGotThemFlyShoes', 'https://res.cloudinary.com/depdfgnxo/image/upload/v1676001393/pexels-ray-piedra-1503009_mzab8r.jpg', 5, 'My second favorite pair of shoes', '2023-02-02 17:45:25-09');
INSERT INTO posts (username, photo_url, likes, caption, date_time)
VALUES ('admin', 'https://res.cloudinary.com/depdfgnxo/image/upload/v1675992688/wbz4rkhymeplf32nh1k3.jpg', 5, 'Mmm tasty bagels', '2023-02-02 17:45:25-10');
INSERT INTO posts (username, photo_url, likes, caption, date_time)
VALUES ('admin', 'https://res.cloudinary.com/depdfgnxo/image/upload/v1675992646/ezvyaryekkxilnd9hwfr.jpg', 5, 'What a great brunch. Delicious fruits', '2023-02-02 17:45:25-11');
INSERT INTO posts (username, photo_url, likes, caption, date_time)
VALUES ('CeilingCantHoldMe', 'https://res.cloudinary.com/depdfgnxo/image/upload/v1675956647/qfpj33dkbtiabqknswmc.jpg', 5, 'This is my new camera', '2023-02-02 17:45:25-12');
INSERT INTO posts (username, photo_url, likes, caption, date_time)
VALUES ('CeilingCantHoldMe', 'https://res.cloudinary.com/depdfgnxo/image/upload/v1675813321/oj9kuy7jprd0j7rwv05o.jpg', 5, 'I took a picture of a camera with a camera', '2023-02-02 17:45:25-13');

INSERT INTO comments (post_id, commenter, comment)
VALUES (1, 'admin', 'Great picture, Rick. Love the beautiful flowers');
INSERT INTO comments (post_id, commenter, comment)
VALUES (1, 'admin', 'Is that a bee I see?!! Be careful');

INSERT INTO comments (post_id, commenter, comment)
VALUES(2, 'user', 'Omg, what artist was performing?');

INSERT INTO comments (post_id, commenter, comment)
VALUES (2, 'IGotThemFlyShoes', 'I was there too!');

INSERT INTO comments (post_id, commenter, comment)
VALUES (3, 'CeilingCantHoldMe', 'They are aight');
INSERT INTO comments (post_id, commenter, comment)
VALUES (3, 'CeilingCantHoldMe', 'I am just hating because I am jealous');

INSERT INTO profiles (user_id, email, name, profile_image, profile_bio)
VALUES (1, 'fakeemail1@yahoo.com', 'Rick Kelly', 'https://i.imgur.com/ZqnpACC.jpg', 'First user on Momento. Cannot belive I got off the waitlist');

INSERT INTO profiles (user_id, email, name, profile_image, profile_bio)
VALUES (2, 'fakeemail2@yahoo.com', 'Mayor Momento', 'https://i.imgur.com/JWiPXH5.jpg', 'Admin of Momento | Creator of Things');

INSERT INTO profiles (user_id, email, name, profile_image, profile_bio)
VALUES (3, 'imincharge@fakemail.org', 'Sarah Woo', 'https://res.cloudinary.com/depdfgnxo/image/upload/v1675111162/cld-sample-5.jpg', 'Hello from Santa Monica. #beachvibes');
INSERT INTO profiles (user_id, email, name, profile_image, profile_bio)
VALUES (4, 'partyanimal@fakemail.org', 'Eric Vasquez', 'https://i.imgur.com/7bikc5q.jpg', 'Here for a good time. Send me your memes #loveMemes');


COMMIT TRANSACTION;
