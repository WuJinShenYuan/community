CREATE TABLE `user`(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	account_id VARCHAR(100),
	name VARCHAR(50),
	token CHAR(36),
	gmt_create BIGINT,
	gmt_modified BIGINT,
    bio VARCHAR(255),
    avatar_url VARCHAR(100)
)ENGINE=Innodb DEFAULT CHARSET=utf8;