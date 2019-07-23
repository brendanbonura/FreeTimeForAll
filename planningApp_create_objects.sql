DROP SCHEMA IF EXISTS planningapp_db;
CREATE SCHEMA planningapp_db;

DROP TABLE IF EXISTS planningapp_db.users;
CREATE TABLE planningapp_db.users (
	user_id INT AUTO_INCREMENT PRIMARY KEY,
	user_email NVARCHAR (255) NOT NULL,
	user_firstName VARCHAR (255) NOT NULL,
	user_lastName VARCHAR (255) NOT NULL,
	user_password CHAR (68) NOT NULL
);

DROP TABLE IF EXISTS planningapp_db.roles;
CREATE TABLE planningapp_db.roles (
	role_id INT AUTO_INCREMENT PRIMARY KEY,
	role_name VARCHAR (255) NOT NULL
);

INSERT INTO planningapp_db.roles
VALUES (1, 'ROLE_USER');
INSERT INTO planningapp_db.roles
VALUES (2, 'ROLE_ADMIN');

DROP TABLE IF EXISTS planningapp_db.users_roles;
CREATE TABLE planningapp_db.users_roles(
	user_id INT NOT NULL,
	role_id INT NOT NULL,
	PRIMARY KEY (user_id, role_id),
	FOREIGN KEY (user_id) REFERENCES planningapp_db.users (user_id),
	FOREIGN KEY (role_id) REFERENCES planningapp_db.roles (role_id)
);

DROP TABLE IF EXISTS planningapp_db.groups;
CREATE TABLE planningapp_db.groups (
	group_id INT AUTO_INCREMENT PRIMARY KEY,
	group_name VARCHAR (255) NOT NULL,
	group_description VARCHAR (255)
);

DROP TABLE IF EXISTS planningapp_db.users_groups;
CREATE TABLE planningapp_db.users_groups (
	user_id INT NOT NULL,
	group_id INT NOT NULL,
	PRIMARY KEY (user_id, group_id),
	FOREIGN KEY (user_id) REFERENCES planningapp_db.users (user_id),
	FOREIGN KEY (group_id) REFERENCES planningapp_db.groups (group_id)
);

DROP TABLE IF EXISTS planningapp_db.availableTimes;
CREATE TABLE planningapp_db.availableTimes (
	availableTimes_id INT AUTO_INCREMENT PRIMARY KEY,
	availableTimes_date DATE NOT NULL,
	availableTimes_startTime TIME NOT NULL,
	availableTimes_endTime TIME NOT NULL,
	user_id INT NOT NULL,
	group_id INT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES planningapp_db.users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (group_id) REFERENCES planningapp_db.groups (group_id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS planningapp_db.Persistent_Logins;
CREATE TABLE planningapp_db.Persistent_Logins (
 
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp not null,
    PRIMARY KEY (series)
     
);
