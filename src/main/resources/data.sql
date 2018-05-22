--INSERT USERS INTO DB
insert into user(id, birthday, description, is_deactivated, is_private, last_online, latitude, longitude, name, online_status, password, profile_created, radius, share_location, surname, swipe_throw, user_gender, username)
	values(1, '1994-02-24', 'Description for user 1', false, false, '2018-05-22', 45.267136, 19.833549, 'Marko', true, 'user1PWD', '2018-05-22', 14.0, true, 'Kljajic', 'FEMAIL', 'MALE', 'username1');

insert into user(id, birthday, description, is_deactivated, is_private, last_online, latitude, longitude, name, online_status, password, profile_created, radius, share_location, surname, swipe_throw, user_gender, username)
	values(2, '1992-06-13', 'Description for user 2', false, false, '2018-05-21', 45.267136, 19.833549, 'Snjezana', true, 'user2PWD', '2018-05-21', 11.0, true, 'Snjeguljica', 'FEMAIL', 'FEMAIL', 'username2');

insert into user(id, birthday, description, is_deactivated, is_private, last_online, latitude, longitude, name, online_status, password, profile_created, radius, share_location, surname, swipe_throw, user_gender, username)
	values(3, '1996-04-07', 'Description for user 3', false, false, '2018-05-22', 45.267136, 19.833549, 'Ruzica', true, 'user3PWD', '2018-05-21', 18.0, true, 'Trnova', 'MALE', 'FEMAIL', 'username3');

insert into user(id, birthday, description, is_deactivated, is_private, last_online, latitude, longitude, name, online_status, password, profile_created, radius, share_location, surname, swipe_throw, user_gender, username)
	values(4, '1994-01-01', 'Description for user 4', false, false, '2018-05-22', 46.100376, 19.667587, 'Darko', true, 'user4PWD', '2018-05-22', 9.0, true, 'Tacic', 'FEMAIL', 'MALE', 'username4');

insert into user(id, birthday, description, is_deactivated, is_private, last_online, latitude, longitude, name, online_status, password, profile_created, radius, share_location, surname, swipe_throw, user_gender, username)
	values(5, '1994-05-05', 'Description for user 5', false, false, '2018-05-22', 44.787197, 20.457273, 'Mirko', true, 'user5PWD', '2018-05-22', 20.0, true, 'Mikac', 'FEMAIL', 'MALE', 'username5');

insert into user(id, birthday, description, is_deactivated, is_private, last_online, latitude, longitude, name, online_status, password, profile_created, radius, share_location, surname, swipe_throw, user_gender, username)
	values(6, '1994-11-11', 'Description for user 6', false, false, '2018-05-22', 44.787197, 20.457273, 'Stefan', true, 'user6PWD', '2018-05-22', 100.0, true, 'Varajic', 'FEMAIL', 'MALE', 'username6');

insert into user(id, birthday, description, is_deactivated, is_private, last_online, latitude, longitude, name, online_status, password, profile_created, radius, share_location, surname, swipe_throw, user_gender, username)
	values(7, '1994-05-05', 'Description for user 7', true, false, '2018-05-22', 45.267136, 19.833549, 'Deaktiviran', true, 'user7PWD', '2018-05-22', 13.0, true, 'Profil', 'MALE', 'MALE', 'deaktivan');
	
--INSERT USER SWIPES INTO DB
insert into swipe(id, is_liked, time_swiped, liked_id, liker_id) values (1, true, '2018-05-22', 2, 1);