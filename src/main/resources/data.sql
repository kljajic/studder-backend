--INSERT USERS INTO DB
insert into user(id, birthday, description, is_deactivated, is_private, last_online, latitude, longitude, name, online_status, password, profile_created, radius, share_location, surname, swipe_throw, user_gender, username, city)
	values(1, '1994-02-24', 'Description for user 1', false, false, '2018-05-22', 45.267136, 19.833549, 'Marko', true, '$2a$10$Afp2qujbAA9SN721xJtLMuVuhQewMA2rHBcQpPwNErpTYI6yn4lY.', '2018-05-22', 14.0, true, 'Kljajic', 'FEMAIL', 'MALE', 'username1', 'Novi Sad');

insert into user(id, birthday, description, is_deactivated, is_private, last_online, latitude, longitude, name, online_status, password, profile_created, radius, share_location, surname, swipe_throw, user_gender, username, city)
	values(2, '1992-06-13', 'Description for user 2', false, false, '2018-05-21', 45.267136, 19.833549, 'Snjezana', true, '$2a$10$jml9.0FXGJ/lzMFE8TfSge8zCxYlCJ4Xe8XquxoXHrB7fTuLoRKY.', '2018-05-21', 11.0, true, 'Snjeguljica', 'FEMAIL', 'FEMAIL', 'username2', 'Novi Sad');

insert into user(id, birthday, description, is_deactivated, is_private, last_online, latitude, longitude, name, online_status, password, profile_created, radius, share_location, surname, swipe_throw, user_gender, username, city)
	values(3, '1996-04-07', 'Description for user 3', false, false, '2018-05-22', 45.267136, 19.833549, 'Ruzica', true, '$2a$10$TUQHIPaByPC4fkAkKA0d2uHOIvuxFphuScCCO2bWOvNclVDK410T6', '2018-05-21', 18.0, true, 'Trnova', 'MALE', 'FEMAIL', 'username3', 'Novi Sad');

insert into user(id, birthday, description, is_deactivated, is_private, last_online, latitude, longitude, name, online_status, password, profile_created, radius, share_location, surname, swipe_throw, user_gender, username, city)
	values(4, '1994-01-01', 'Description for user 4', false, false, '2018-05-22', 46.100376, 19.667587, 'Darko', true, '$2a$10$shRcJ2DcdxCHSv5zhPaQ4.tg16ZMb0nxSHFW8Tg/cB4x0w5J5uptS', '2018-05-22', 9.0, true, 'Tacic', 'FEMAIL', 'MALE', 'username4', 'Novi Sad');

insert into user(id, birthday, description, is_deactivated, is_private, last_online, latitude, longitude, name, online_status, password, profile_created, radius, share_location, surname, swipe_throw, user_gender, username, city)
	values(5, '1994-05-05', 'Description for user 5', false, false, '2018-05-22', 44.787197, 20.457273, 'Mirko', true, '$2a$10$xbVo10sd0F4zkAeBsALv8O7fGdAzAyHiTlrOPmqedq4e3PstT7bqO', '2018-05-22', 20.0, true, 'Mikac', 'FEMAIL', 'MALE', 'username5', 'Novi Sad');

insert into user(id, birthday, description, is_deactivated, is_private, last_online, latitude, longitude, name, online_status, password, profile_created, radius, share_location, surname, swipe_throw, user_gender, username, city)
	values(6, '1994-11-11', 'Description for user 6', false, false, '2018-05-22', 44.787197, 20.457273, 'Stefan', true, '$2a$10$UHsiZcU0PZikjRq6QW3y4ub0KMlG9madup6amJn8fD6wRFp9wDyXm', '2018-05-22', 100.0, true, 'Varajic', 'FEMAIL', 'MALE', 'username6', 'Novi Sad');

insert into user(id, birthday, description, is_deactivated, is_private, last_online, latitude, longitude, name, online_status, password, profile_created, radius, share_location, surname, swipe_throw, user_gender, username, city)
	values(7, '1994-05-05', 'Description for user 7', true, false, '2018-05-22', 45.267136, 19.833549, 'Deaktiviran', true, '$2a$10$kM4FdWCmb.BSyXJdqOBAhuQB1F25QEKWdfbS7ViS6LkSpNF.Th0M2', '2018-05-22', 13.0, true, 'Profil', 'MALE', 'MALE', 'deaktivan', 'Novi Sad');
	
--INSERT USER SWIPES INTO DB
insert into swipe(id, is_liked, time_swiped, liked_id, liker_id) values (1, true, '2018-05-22', 2, 1);

insert into user_match(id, participant1_id, participant2_id, last_message, last_message_seen, muted_participant1, muted_participant2, last_message_date) values (1, 1, 2, "Hi, this is a message 1", false, false, false, '2018-05-22');
insert into user_match(id, participant1_id, participant2_id, last_message, last_message_seen, muted_participant1, muted_participant2, last_message_date) values (2, 1, 3, "Hi, this is a message 2", false, true, true, '2018-05-22');

insert into user_match(id, participant1_id, participant2_id, last_message, last_message_seen, muted_participant1, muted_participant2, last_message_date) values (3, 2, 3, "Hi, this is a message 3", false, false, false, '2018-05-22');
insert into user_match(id, participant1_id, participant2_id, last_message, last_message_seen, muted_participant1, muted_participant2, last_message_date) values (4, 2, 4, "Hi, this is a message 4", false, false, true, '2018-05-22');
insert into user_match(id, participant1_id, participant2_id, last_message, last_message_seen, muted_participant1, muted_participant2, last_message_date) values (5, 2, 5, "Hi, this is a message 5", false, false, false, '2018-05-22');

insert into user_match(id, participant1_id, participant2_id, last_message, last_message_seen, muted_participant1, muted_participant2, last_message_date) values (6, 3, 4, "Hi, this is a message 6", false, true, true, '2018-05-22');
insert into user_match(id, participant1_id, participant2_id, last_message, last_message_seen, muted_participant1, muted_participant2, last_message_date) values (7, 3, 5, "Hi, this is a message 7", false, false, false, '2018-05-22');
insert into user_match(id, participant1_id, participant2_id, last_message, last_message_seen, muted_participant1, muted_participant2, last_message_date) values (8, 3, 6, "Hi, this is a message 8", false, false, false, '2018-05-22');
insert into user_match(id, participant1_id, participant2_id, last_message, last_message_seen, muted_participant1, muted_participant2, last_message_date) values (9, 3, 7, "Hi, this is a message 9", false, false, false, '2018-05-22');

insert into user_match(id, participant1_id, participant2_id, last_message, last_message_seen, muted_participant1, muted_participant2, last_message_date) values (10, 4, 5, "Hi, this is a message 10", false, false, false, '2018-05-22');
insert into user_match(id, participant1_id, participant2_id, last_message, last_message_seen, muted_participant1, muted_participant2, last_message_date) values (11, 4, 6, "Hi, this is a message 11", false, false, false, '2018-05-22');

insert into media(id, path, description, time_added, is_private, user_id) values (1, "media/png1.jpg", "Some description 1", '2018-05-05', false, 1);
insert into media(id, path, description, time_added, is_private, user_id) values (2, "media/png2.jpg", "Some description 2", '2018-05-05', false, 1);
insert into media(id, path, description, time_added, is_private, user_id) values (3, "media/png3.jpg", "Some description 3", '2018-05-05', false, 1);
insert into media(id, path, description, time_added, is_private, user_id) values (4, "media/png4.jpg", "Some description 4", '2018-05-05', false, 1);
insert into media(id, path, description, time_added, is_private, user_id) values (5, "media/png5.jpg", "Some description 5", '2018-05-05', false, 1);
insert into media(id, path, description, time_added, is_private, user_id) values (6, "media/png6.jpg", "Some description 6", '2018-05-05', false, 1);