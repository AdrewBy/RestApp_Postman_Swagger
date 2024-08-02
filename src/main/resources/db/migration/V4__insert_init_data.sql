/*Populate users*/
INSERT INTO users (name) values ('user_1');
INSERT INTO users (name) values ('user_2');
INSERT INTO users (name) values ('user_3');
INSERT INTO users (name) values ('user_4');


/*Populate files*/
INSERT INTO files (name,filePath) values ('file_1','home/path_1');
INSERT INTO files (name,filePath) values ('file_2','home/path_2');
INSERT INTO files (name,filePath) values ('file_3','home/path_3');

/*Populate events*/
INSERT INTO events (user_id,file_id) values (1,2);
INSERT INTO events (user_id,file_id) values (2,2);
INSERT INTO events (user_id,file_id) values (1,3);
INSERT INTO events (user_id,file_id) values (2,1);
INSERT INTO events (user_id,file_id) values (4,1);
INSERT INTO events (user_id,file_id) values (2,3);