create table user1
(
	id int auto_increment not null,
	account_id varchar(100),
	name varchar(50),
	token char(36),
	gmt_create bigint,
	gmt_modified bigint,
	constraint user1_pk
		primary key (id)
);

