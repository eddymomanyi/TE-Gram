BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, profiles, posts, comments, ratings, favorites;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE profiles (
    profile_id SERIAL NOT NULL,
    user_id integer NOT NULL,
    email varchar(100) NOT NULL,
    name varchar(100) NOT NULL,
    profile_image varchar(100),
    profile_bio varchar(300),
    CONSTRAINT PK_profile PRIMARY KEY (profile_id),
    CONSTRAINT FK_profile_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE posts (
    post_id SERIAL NOT NULL,
    username varchar(100) NOT NULL,
    photo_url varchar(100) NOT NULL,
    likes integer NOT NULL,
    caption varchar(300),
    date_time TIMESTAMPTZ,
    CONSTRAINT PK_post PRIMARY KEY (post_id),
    CONSTRAINT FK_post_user FOREIGN KEY (username) REFERENCES users (username)
);

CREATE TABLE comments (
    comment_id SERIAL NOT NULL,
    post_id int NOT NULL,
	commenter varchar(50) NOT NULL,
    comment varchar(300) NOT NULL,
    CONSTRAINT PK_comment PRIMARY KEY (comment_id),
    CONSTRAINT FK_comment_post FOREIGN KEY (post_id) REFERENCES posts (post_id),
	CONSTRAINT FK_commenter FOREIGN KEY (commenter) REFERENCES users (username) 
);

CREATE TABLE ratings (
    rating_id SERIAL NOT NULL,
    post_id int NOT NULL,
    rating decimal(3,2) NOT NULL,
    rater varchar(50) NOT NULL,
    CONSTRAINT PK_rating PRIMARY KEY (rating_id),
    CONSTRAINT FK_rating_post FOREIGN KEY (post_id) REFERENCES posts (post_id),
    CONSTRAINT FK_rating_user FOREIGN KEY (rater) REFERENCES users (username)
);

CREATE TABLE favorites (
username varchar(50),
post_id int,
CONSTRAINT PK_favorites PRIMARY KEY (username, post_id),
CONSTRAINT FK_fav_username FOREIGN KEY (username) REFERENCES users (username),
CONSTRAINT FK_fav_post FOREIGN KEY (post_id) REFERENCES posts (post_id)
);

COMMIT TRANSACTION;
