SET FOREIGN_KEY_CHECKS=0;
truncate table users ;
truncate table media ;
SET FOREIGN_KEY_CHECKS=1;


insert into users(id, email, password, time_created) values
(200, 'victormsonter@gmail.com', '$2a$10$cYjoMXC5eH308mRRAljqk.QyWjcrI0hXgAhlnPup2vUgM4EHV95Vq', '2024-06-04T15:03:03.792009700'),
(201, 'vic@gmail.com', '$2a$10$cYjoMXC5eH308mRRAljqk.QyWjcrI0hXgAhlnPup2vUgM4EHV95Vq', '2024-06-04T15:03:03.792009700'),
(202, 'victor@gmail.com', '$2a$10$cYjoMXC5eH308mRRAljqk.QyWjcrI0hXgAhlnPup2vUgM4EHV95Vq', '2024-06-04T15:03:03.792009700'),
(203, 'msonter@gmail.com', '$2a$10$cYjoMXC5eH308mRRAljqk.QyWjcrI0hXgAhlnPup2vUgM4EHV95Vq', '2024-06-04T15:03:03.792009700');

insert into media(id, url, description, category, time_created, uploader_id)values
(100, 'https://www.cloudinary.com/media1', 'media 1', 'ACTION',  '2024-06-04T15:03:03.792009700', 200),
(101, 'https://www.cloudinary.com/media2', 'media 2', 'ACTION',  '2024-06-04T15:03:03.792009700', 200),
(102, 'https://www.cloudinary.com/media3', 'media 3', 'ROMANCE',  '2024-06-04T15:03:03.792009700', 200),
(103, 'https://www.cloudinary.com/media3', 'media 4', 'ROMANCE',  '2024-06-04T15:03:03.792009700', 200);
