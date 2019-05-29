create table USER1
(
	ID INT auto_increment NOT NULL,
	ACCOUNT_ID VARCHAR(100),
	NAME VARCHAR(50),
	TOKEN CHAR(36),
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	constraint USER1_PK
		primary key (ID)
);

