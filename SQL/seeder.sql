USE lostbutfound_db;


TRUNCATE categories;

INSERT INTO categories (property_type)
VALUES ('Phones'),
       ('Tablets'),
       ('Headphones'),
       ('Laptops'),
       ('Electronics'),
       ('Keys'),
       ('Glasses'),
       ('Wallets'),
       ('Jewelry'),
       ('Purses'),
       ('Makeup bag'),
       ('Hats'),
       ('Jackets'),
       ('Shoes'),
       ('Clothing'),
       ('Backpacks'),
       ('Cameras'),
       ('Passports'),
       ('Luggage'),
       ('Water bottles'),
       ('Toys'),
       ('Books'),
       ('Other');


TRUNCATE locations;

INSERT INTO locations (location_name, location_zip)
VALUES ('San Antonio Airport', 12345),
       ('River Walk', 12345),
       ('Alamo area', 12345),
       ('North Star Mall', 12345),
       ('Shop at La Cantera', 12345),
       ('Ingram Park Mall', 12345),
       ('South Park Mall', 12345),
       ('Rolling Oaks Mall', 12345),
       ('River Center Mall', 12345),
       ('Morgan''s Wonderland', 12345),
       ('AT&T Center', 12345),
       ('Henry B. Gonzales Convention Center', 12345),
       ('Freeman Coliseum', 12345),
       ('Alamo Dome', 12345),
       ('Majestic Theatre', 12345),
       ('San Antonio Museum of Art', 12345),
       ('Witte Museum', 12345),
       ('The DoSeum', 12345),
       ('San Antonio Zoo', 12345),
       ('San Antonio Botanical Garden', 12345),
       ('Japanese Tea Gardens', 12345),
       ('The Pearl', 12345),
       ('Hemisphere Park', 12345),
       ('King William District', 12345),
       ('Fort Sam Houston', 12345),
       ('Lackland Air Force Base', 12345),
       ('Randolph Air Force Base', 12345),
       ('Market Square', 12345),
       ('Farmers Market', 12345),
       ('Natural Bridge Caverns', 12345),
       ('San Pedro Springs Park', 12345),
       ('Six Flags Fiesta Texas', 12345),
       ('Splashtown', 12345),
       ('Mission San Jose', 12345),
       ('Traders Village', 12345),
       ('Other', 12345);

dokku mysql:connect lostbutfound_db
drop database lostbutfound_db; create database lostbutfound_db;
exit;
dokku ps:rebuild lostbutfound_db

dokku mysql:connect lostbutfound_db

dokku mysql:link lostbutfound_db lostbutfound.xyz

