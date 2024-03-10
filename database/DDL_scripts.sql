create database if not exists DB_Thrillio;

use DB_Thrillio;

create table if not exists Author 
(id bigint primary key auto_increment,
name varchar(200) not null);

create table if not exists Publisher 
(id bigint primary key auto_increment,
name varchar(200) not null);

create table if not exists Book(id bigint PRIMARY KEY
auto_increment,
title varchar(500) not null,
publication_year int,
publisher_id bigint,
book_genre_id TINYINT,
amazon_rating double,
kid_friendly_status TINYINT,
created_date datetime not null,
FOREIGN KEY (publisher_id) REFERENCES Publisher(id) on delete set null on update cascade,
CONSTRAINT UNIQUE (title (250), publication_year, publisher_id)); 
-- this CONSTRAINT UNIQUE specify the candidate key.

create table if not exists Book_Author (id bigint PRIMARY KEY auto_increment,
book_id bigint not null,
author_id bigint not null,
FOREIGN KEY (book_id) REFERENCES Book(id),
FOREIGN KEY (author_id) REFERENCES Author(id),
constraint UNIQUE(book_id, author_id));


INSERT INTO Author (name) VALUES  ('Henry DavidThoreau'),
                                  ('Ralph Waldo Emerson'),
                                  ('Lillian Eichler Watson'),
                                  ('Eric Freeman'),
                                  ('Bert Bates'), 
                                  ('Kathy Sierra'), 
                                  ('Elisabeth Robson'),
								  ('Joshua Bloch');
				
INSERT INTO Publisher (name) VALUES ('Wilder Publications'),
									('Dover Publications'),
                                    ('Touchstone'),
                                    ("O'Reilly Media"),
                                    ('Prentice Hall');
                                    
INSERT INTO Book (title, publication_year,
publisher_id, book_genre_id, amazon_rating,
kid_friendly_status, created_date) 
VALUES 
('Walden', 1854, 1, 6, 4.3, 2, NOW()),
('Self-Reliance and Other Essays', 1993, 2, 6, 4.5, 2, NOW()),
('Light From Many Lamps', 1988, 3, 6, 5.0, 2, NOW()),
('Head First Design Patterns', 2004, 4, 10, 4.5, 2, NOW()),
('Effective Java Programming Language Guide', 2007, 5, 10, 4.9, 2, NOW());

INSERT INTO Book_Author (book_id, author_id) 
VALUES (1, 1), (2, 2), (3, 3), (4, 4), (4, 5), (4, 6), (4, 7), (5, 8);

create table if not exists Actor(id bigint PRIMARY KEY auto_increment, 
name varchar (200),
constraint unique (name(200)));

create table if not exists Director(id bigint PRIMARY KEY auto_increment,
name varchar (200),
constraint unique (name(200)));

create table if not exists  Movie(id bigint PRIMARY KEY auto_increment,
title varchar(500) not null,
release_year int,
movie_genre_id TINYINT,
imdb_rating double,
kid_friendly_status TINYINT,
created_date datetime not null,
CONSTRAINT UNIQUE(title (100), release_year));

create table Movie_Actor(id bigint PRIMARY KEY auto_increment,
movie_id bigint not null,
actor_id bigint not null,
FOREIGN KEY (movie_id) REFERENCES movie(id),
FOREIGN KEY (actor_id) REFERENCES actor(id),
CONSTRAINT UNIQUE(movie_id, actor_id));

create table if not exists Movie_Director(id bigint PRIMARY KEY auto_increment,
movie_id bigint not null,
director_id bigint not null,
FOREIGN KEY (movie_id) REFERENCES movie(id),
FOREIGN KEY (director_id) REFERENCES director(id),
CONSTRAINT UNIQUE(movie_id, director_id));

INSERT INTO actor (name) VALUES 
('Orson Welles'), ('Joseph Cotten'), ('Henry Fonda'), 
('Jane Darwell'), ('Albert Cullum'), ('Kaley Cuoco'), 
('Jim Parsons'), ('Takashi Shimura'), ('Minoru Chiaki');

INSERT INTO director (name) VALUES 
('Orson Welles'), ('John Ford'), ('Leslie Sullivan'), 
('Chuck Lorre'), ('Bill Prady'), ('Akira Kurosawa');

INSERT INTO movie (title, release_year, movie_genre_id, imdb_rating, kid_friendly_status, created_date) VALUES 
('Citizen Kane', 1941, 0, 8.5, 2, NOW()),
('The Grapes of Wrath', 1940, 0, 8.2, 2, NOW()),
('A Touch of Greatness', 2004, 24, 7.3, 2, NOW()),
('The Big Bang Theory', 2007, 20, 8.7, 2, NOW()),
('Ikiru', 1952, 25, 8.4, 2, NOW());

INSERT INTO movie_actor (movie_id, actor_id) VALUES
(1, 1), (1, 2), (2, 3), (2, 4), (3, 5), (4, 6), (4,
7), (5, 8), (5, 9);

INSERT INTO movie_director(movie_id, director_id)
VALUES (1, 1), (2, 2), (3, 3), (4, 4), (4, 5), (5,
6);


create table if not exists WebLink(id bigint PRIMARY KEY auto_increment,
title varchar(500) not null,
url varchar(250) not null,
host varchar(250) not null,
kid_friendly_status TINYINT,
created_date datetime not null,
CONSTRAINT UNIQUE(url (200)));

INSERT INTO WebLink (title, url, host, kid_friendly_status, created_date) VALUES 
('Use Final Liberally', 'http://www.javapractices.com/topic/TopicAction.do?Id=23', 'http://www.javapractices.com', 2, NOW()),
('How do I import a pre-existing Java project into Eclipse and get up and running?', 'https://stackoverflow.com/questions/142863/how-do-i-import-a-pre-existing-java-project-into-eclipse-and-get-up-and-running', 'http://stackoverflow.com', 2, NOW()),
('Interface vs Abstract Class', 'http://mindprod.com/jgloss/interfacevsabstract.html', 'http://mindprod.com', 2, NOW()),
('NIO tutorial by Greg Travis', 'http://cs.brown.edu/courses/cs161/papers/j-nio-ltr. pdf', 'http://cs.brown.edu', 2, NOW()),
('Virtual Hosting and Tomcat', 'http://tomcat.apache.org/tomcat-6.0-doc/virtual-hosting-howto.html', 'http://tomcat.apache.org', 2, NOW());

create table if not exists User(id bigint PRIMARY KEY auto_increment,
email varchar(100) not null,
password varchar(50) not null,
first_name varchar(100) not null,
last_name varchar(100) not null,
gender_id TINYINT,
user_type_id TINYINT,
created_date datetime not null,
CONSTRAINT UNIQUE(email));

INSERT INTO User (email, password, first_name, last_name, gender_id, user_type_id, created_date) VALUES
('user0@semanticsquare.com', 'test', 'John', 'M', 0, 0, NOW()),
('user1@semanticsquare.com', 'test', 'Sam', 'M', 0, 0, NOW()),
('user2@semanticsquare.com', 'test', 'Anita', 'M', 1, 1, NOW()),
('user3@semanticsquare.com', 'test', 'Sara', 'M', 1, 1, NOW()),
('user4@semanticsquare.com', 'test', 'Dheeru', 'M', 0, 2, NOW());


create table if not exists User_Movie(id bigint PRIMARY KEY auto_increment,
user_id bigint not null,
movie_id bigint not null,
FOREIGN KEY (movie_id) REFERENCES Movie(id),
FOREIGN KEY (user_id) REFERENCES User(id),
CONSTRAINT UNIQUE(movie_id, user_id));

create table if not exists User_Book(id bigint PRIMARY KEY auto_increment,
user_id bigint not null,
book_id bigint not null,
FOREIGN KEY (book_id) REFERENCES Book(id),
FOREIGN KEY (user_id) REFERENCES User(id),
CONSTRAINT UNIQUE(book_id, user_id));

create table if not exists User_WebLink(id bigint PRIMARY KEY auto_increment,
user_id bigint not null,
weblink_id bigint not null,
FOREIGN KEY (weblink_id) REFERENCES WebLink(id),
FOREIGN KEY (user_id) REFERENCES User(id),
CONSTRAINT UNIQUE(weblink_id, user_id));

ALTER TABLE Book ADD COLUMN kid_friendly_marked_by
bigint AFTER kid_friendly_status,
ADD COLUMN shared_by bigint AFTER
kid_friendly_marked_by,ADD FOREIGN KEY (kid_friendly_marked_by) REFERENCES
User(id), ADD FOREIGN KEY (shared_by) REFERENCES User(id);


ALTER TABLE WebLink ADD COLUMN
kid_friendly_marked_by bigint AFTER
kid_friendly_status,
ADD COLUMN shared_by bigint AFTER kid_friendly_marked_by,
ADD FOREIGN KEY (kid_friendly_marked_by) REFERENCES User(id),
ADD FOREIGN KEY (shared_by) REFERENCES User(id);


ALTER TABLE Movie ADD COLUMN kid_friendly_marked_by
bigint AFTER kid_friendly_status,
ADD FOREIGN KEY (kid_friendly_marked_by) REFERENCES User(id);


ALTER TABLE Book ADD COLUMN image_url varchar(500) AFTER title;

UPDATE BOOK SET image_url = 'https://images.gr-assets.com/books/1465675526l/16902.jpg' where id=1;
UPDATE BOOK SET image_url = 'https://images.gr-assets.com/books/1520778510l/123845.jpg' where id=2;
UPDATE BOOK SET image_url = 'https://images.gr-assets.com/books/1347739312l/1270698.jpg' where id=3;
UPDATE BOOK SET image_url = 'https://images.gr-assets.com/books/1408309444l/58128.jpg' where id=4;
UPDATE BOOK SET image_url = 'https://images.gr-assets.com/books/1433511045l/105099.jpg' where id=5;

