## 无尽深渊

## 资料
[参考: es社区](https://elasticsearch.cn/)  
[视频教程](https://www.bilibili.com/video/BV1r4411r7au?p=4)  
[Spring 文档](https://spring.io/guides)  
[Thymeleaf 文档](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)  
[Bootstrap 文档](https://www.bootcss.com/)  
[Spring Web](https://spring.io/guides/gs/serving-web-content/)  
[Github deploy key](https://docs.github.com/en/developers/overview/managing-deploy-keys)  
[Github OAuth](https://docs.github.com/en/developers/apps/creating-an-oauth-app)  
[授权OAuth应用](https://docs.github.com/en/developers/apps/authorizing-oauth-apps)

## 工具
[Git](https://git-scm.com/download)  
[Bootstrap](https://v3.bootcss.com/getting-started/#download)  
[okHttp](https://square.github.io/okhttp/)  
[jQuery](https://jquery.com/download/)  
[Lombok](https://projectlombok.org/)  

## 脚本
```创建user表
CREATE TABLE `user`(
	id INT AUTO_INCREMENT PRIMARY KEY,
	account_id VARCHAR(100),
	name VARCHAR(50),
	token CHAR(36),
	gmt_create BIGINT,
	gmt_modified BIGINT
)ENGINE=Innodb DEFAULT CHARSET=utf8;
```
```创建question表
CREATE TABLE `question`(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	title VARCHAR(50),
	description LONGTEXT,
	gmt_create BIGINT,
	gmt_modified BIGINT,
	creator INT,
	comment_count INT DEFAULT 0,
	view_count INT DEFAULT 0,
	like_count INT DEFAULT 0,
	tag VARCHAR(255)
)ENGINE=Innodb DEFAULT CHARSET=utf8;
```